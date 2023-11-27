package seproject.yudelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seproject.yudelivery.entity.OrderFoodEntity;

import java.util.List;
import java.util.Optional;

public interface OrderFoodRepository extends JpaRepository<OrderFoodEntity,Long> {

    List<OrderFoodEntity> findAllByOrder_Id(Long orderId);

}
