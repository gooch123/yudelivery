package seproject.yudelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import seproject.yudelivery.entity.FoodEntity;

@Repository
public interface FoodRepository extends JpaRepository<FoodEntity, Long> {
    Optional<FoodEntity> findById(Long id);
    // 다른 필요한 메서드
}
