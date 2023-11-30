// RiderService.java
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

    // 주문자 ID로 Rider 정보 가져오기
    public Optional<RiderEntity> getRiderByOrderId(Long orderId) {
        return riderRepository.findByCustomerId(orderId);
    }

    // 배달완료로 상태 변경
    public void completeDelivery(Long riderId) {
        riderRepository.updateDeliveryStatusToDelivered(riderId);
    }

    // 추가
}
