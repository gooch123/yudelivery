package seproject.yudelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seproject.yudelivery.entity.RiderEntity;

import java.util.Optional;

public interface RiderRepository extends JpaRepository<RiderEntity, Long> {

    // 주문자 ID로 RiderEntity 찾기
    Optional<RiderEntity> findByOrderId(Long customerId);

    // 배달 상태를 '배달완료'로 변경
   void updateDeliveryStatusToDelivered(Long riderId);
}
