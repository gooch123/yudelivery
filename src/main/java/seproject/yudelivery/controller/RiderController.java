package seproject.yudelivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seproject.yudelivery.dto.RiderDTO;
import seproject.yudelivery.entity.RiderEntity;
import seproject.yudelivery.repository.RiderRepository;

@RestController
@RequestMapping("/riders")
public class RiderController {

    @Autowired
    private RiderRepository riderRepository;

    @PostMapping("/add")
    public void addRider(@RequestBody RiderDTO riderDTO) {
        RiderEntity riderEntity = new RiderEntity();
        // Map the fields from DTO to Entity
        // You can use a library like ModelMapper for this purpose
        riderEntity.setId(riderDTO.getId());
        riderEntity.setDeliveryStatus(riderDTO.getDeliveryStatus());
        riderEntity.setDeliveryAddress1(riderDTO.getDeliveryAddress1());
        riderEntity.setDeliveryAddress2(riderDTO.getDeliveryAddress2());
        riderEntity.setDeliveryAddress3(riderDTO.getDeliveryAddress3());
        riderEntity.setPhone(riderDTO.getPhone());

        riderRepository.save(riderEntity);
    }

    // Add other CRUD operations as needed
}
