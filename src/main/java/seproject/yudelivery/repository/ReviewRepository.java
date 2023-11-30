package seproject.yudelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seproject.yudelivery.entity.ReviewEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findAllByStoreId(Long storeId);
    List<ReviewEntity> findAllByCustomer_Id(Long customerId);
}
