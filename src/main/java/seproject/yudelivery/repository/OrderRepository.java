package seproject.yudelivery.repository;

import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import seproject.yudelivery.entity.OrderEntity;
import java.util.*;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    /** 사용자 주문 조회 */
    List<OrderEntity> findAllByUserId(Long userId);

    /** 상세 주문 조회 */
    Optional<OrderEntity> findById(Long orderId);

    /** 주문 생성 */
    void saveNewOrder(OrderEntity order);
    
    /** 주문 수정 */
    void updateOrder(OrderEntity order);

    /** 주문 삭제 */
    void deleteOrder(Long orderId);
}
