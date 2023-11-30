package seproject.yudelivery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seproject.yudelivery.entity.RiderEntity;
import seproject.yudelivery.repository.RiderRepository;

import java.util.Optional;

@Service
public class RiderService {

    private final RiderRepository riderRepository;

    @Autowired
    public RiderService(RiderRepository riderRepository) {
        this.riderRepository = riderRepository;
    }

    public Optional<RiderEntity> getRiderByOrderId(Long orderId) {
        return riderRepository.findByCustomerId(orderId);
    }

    public void completeDelivery(Long riderId) {
        riderRepository.updateDeliveryStatusToDelivered(riderId);
    }

    // 추가적인 비즈니스 로직이나 메서드를 추가할 수 있습니다.
}
