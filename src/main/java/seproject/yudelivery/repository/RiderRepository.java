package seproject.yudelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seproject.yudelivery.entity.RiderEntity;

import java.util.Optional;

public interface RiderRepository extends JpaRepository<RiderEntity, Long> {

    Optional<RiderEntity> findByCustomerId(Long customerId);

    void updateDeliveryStatusToDelivered(Long riderId);
}
