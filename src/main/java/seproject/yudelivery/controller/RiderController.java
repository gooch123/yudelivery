package seproject.yudelivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import seproject.yudelivery.dto.LocationDTO;
import seproject.yudelivery.dto.UserRole;
import seproject.yudelivery.entity.RiderEntity;
import seproject.yudelivery.entity.UserEntity;
import seproject.yudelivery.repository.RiderRepository;
import seproject.yudelivery.service.OrderService;
import seproject.yudelivery.service.RiderService;

import java.util.Optional;

@Controller
@RequestMapping("/rider")
public class RiderController {

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private RiderService riderService;

    @Autowired
    private OrderService orderService;

    @GetMapping()
    public String riderMain(@SessionAttribute(name = "user",required = false) UserEntity user){
        if(user == null || user.getRole() != UserRole.RIDER){
            return "redirect:/login";
        }
        return "rider/main";
    }

    @GetMapping("/getByCustomerId/{customerId}")
    public String getRiderByCustomerId(@PathVariable Long customerId, Model model) {
        Optional<RiderEntity> riderEntity = riderRepository.findByCustomerId(customerId);
        model.addAttribute("rider", riderEntity.orElse(null));
        return "rider/log";
    }

    @PutMapping("/completeDelivery/{riderId}")
    public String completeDelivery(@PathVariable Long riderId) {
        Optional<RiderEntity> optionalRider = riderRepository.findById(riderId);
        if (optionalRider.isPresent()) {
            RiderEntity rider = optionalRider.get();
            rider.setDeliveryStatus("DELIVERED");
            riderRepository.save(rider);
        }
        return "redirect:/riders/main";
    }

    @PatchMapping("/updateLocation/{riderId}")
    public String updateRiderLocation(@PathVariable Long riderId, @RequestBody LocationDTO locationDTO) {
        riderService.updateRiderLocation(riderId, locationDTO);
        return "redirect:/rider/main";
    }

    @PatchMapping("/cancelDelivery/{riderId}")
    public String cancelDelivery(@PathVariable Long riderId) {
        riderService.cancelDelivery(riderId);
        return "redirect:/rider/main";
    }


    // 수정된 컨트롤러 메소드
    @GetMapping("/rider/order_info")
    public String showOrderInfoPage() {
        return "rider/order_info";
    }

    @PostMapping("/updateStatus")
    public String updateStatus(@RequestParam String orderID, @RequestParam String status) {

        if(status.equals("배송 완료")) {
            System.out.println("주문 ID: " + orderID + ", 새로운 상태: " + status);
            orderService.completeOrder(Long.parseLong(orderID));
        }

        return "redirect:/rider";
    }

    @PostMapping("/acceptAndCancel")
    public String acceptAndCancel(@RequestParam String orderDistance) {
        System.out.println("주문 거리: " + orderDistance);
        return "redirect:/rider/main";
    }

    @GetMapping("/order_info")
    public String showOrderInfoPage(Model model) {
        return "rider/order_info";
    }



}
