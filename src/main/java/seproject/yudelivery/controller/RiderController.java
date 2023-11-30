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

    @GetMapping("/getByCustomerId/{customerId}")
    public Optional<RiderEntity> getRiderByCustomerId(@PathVariable Long customerId) {
        return riderRepository.findByCustomerId(customerId);
    }

    @PutMapping("/completeDelivery/{riderId}")
    public void completeDelivery(@PathVariable Long riderId) {
        Optional<RiderEntity> optionalRider = riderRepository.findById(riderId);
        if (optionalRider.isPresent()) {
            RiderEntity rider = optionalRider.get();
            rider.setDeliveryStatus("DELIVERED");
            riderRepository.save(rider);
        }
    }

    // Add other CRUD operations as needed
}
