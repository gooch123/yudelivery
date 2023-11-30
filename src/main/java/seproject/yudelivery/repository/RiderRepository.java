package seproject.yudelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seproject.yudelivery.entity.RiderEntity;

import java.util.List;
import java.util.Optional;

public interface RiderRepository extends JpaRepository<RiderEntity, Long> {

    // 주문자 ID로 RiderEntity 찾기
    List<RiderEntity> findAllByCustomerId(Long customerId);

    // Optional<RiderEntity> findByCustomerId(Long customerId); // Alternatively, if you expect at most one result

}
