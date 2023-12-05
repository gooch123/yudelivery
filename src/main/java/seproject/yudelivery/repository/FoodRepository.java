package seproject.yudelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import seproject.yudelivery.entity.FoodEntity;

@Repository
public interface FoodRepository extends JpaRepository<FoodEntity, Long> {
    List<FoodEntity> findAllByStoreId(Long storeId);
    Optional<FoodEntity> findById(Long id);

    @Query("SELECT f FROM FoodEntity f WHERE f.food_name = :foodName")
    Optional<FoodEntity> findFoodExisting(@Param("foodName") String foodName); // 음식이 이미 존재하는지 확인하는 메서드

    default FoodEntity saveNewFood(FoodEntity food) {
        return save(food); // 새로운 음식 저장
    }

    default void deleteFood(Long id) {
        deleteById(id); // 음식 삭제
    }

    default FoodEntity updateFood(FoodEntity food)   {
        return save(food); // 음식 업데이트
    }
}
