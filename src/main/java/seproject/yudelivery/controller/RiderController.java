package seproject.yudelivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import seproject.yudelivery.dto.LocationDTO;
import seproject.yudelivery.entity.RiderEntity;
import seproject.yudelivery.repository.RiderRepository;
import seproject.yudelivery.service.RiderService;

import java.util.Optional;

@Controller
@RequestMapping("/riders")
public class RiderController {

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private RiderService riderService;

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
        return "redirect:/riders/main";
    }

    @PatchMapping("/cancelDelivery/{riderId}")
    public String cancelDelivery(@PathVariable Long riderId) {
        riderService.cancelDelivery(riderId);
        return "redirect:/riders/main";
    }

    @GetMapping("/main")
    public String showMainPage() {
        return "rider/main";
    }

    @GetMapping("/order_info")
    public String showOrderInfoPage() {
        return "rider/order_info";
    }
}
