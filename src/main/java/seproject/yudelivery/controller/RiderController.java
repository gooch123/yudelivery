package seproject.yudelivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seproject.yudelivery.entity.RiderEntity;
import seproject.yudelivery.repository.RiderRepository;

import java.util.Optional;

@RestController
@RequestMapping("/riders")
public class RiderController {

    @Autowired
    private RiderRepository riderRepository;

    // 주문자 ID로 Rider 정보 가져오기
    @GetMapping("/getByorderId/{orderId}")
    /*public Optional<RiderEntity> getRiderByOrderId(@PathVariable Long orderId) {
        return riderRepository.findByOrderId(orderId);
    }*/

    // 배달완료로 상태 변경
    @PutMapping("/completeDelivery/{riderId}")
    public void completeDelivery(@PathVariable Long riderId) {
//        riderRepository.updateDeliveryStatusToDelivered(riderId);
    }

    // Add other CRUD operations as needed
}
