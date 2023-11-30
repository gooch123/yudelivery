// RiderRepository.java
package seproject.yudelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seproject.yudelivery.entity.RiderEntity;

import java.util.Optional;

public interface RiderRepository extends JpaRepository<RiderEntity, Long> {

    Optional<RiderEntity> findByCustomerId(Long customerId);

    // Optional<RiderEntity> findById(Long riderId); // Uncomment this if you need to find by rider ID

    // 추가
    void updateDeliveryStatusToDelivered(Long riderId);
}
