package seproject.yudelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seproject.yudelivery.entity.RiderEntity;

@Repository
public interface RiderRepository extends JpaRepository<RiderEntity, Long> {

    // You can add custom query methods here if needed
}
