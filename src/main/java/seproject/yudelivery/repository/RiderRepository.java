package seproject.yudelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import seproject.yudelivery.entity.RiderEntity;

import java.util.Optional;

public interface RiderRepository extends JpaRepository<RiderEntity, Long> {

    // 주문자 ID로 RiderEntity 찾기
    Optional<RiderEntity> findByOrderId(Long customerId);


}
