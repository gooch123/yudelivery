package seproject.yudelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seproject.yudelivery.entity.CustomerEntity;
import seproject.yudelivery.entity.OrderEntity;

import java.util.*;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    /** 사용자 주문 조회 */
    List<OrderEntity> findAllByCustomer_Id(Long customerId);

    /** 상세 주문 조회 */
    Optional<OrderEntity> findById(Long id);

    /** 주문 생성 */
//    void saveNewOrder(OrderEntity order); //에러 발생

    /** 주문 수정 */
//    void updateOrder(OrderEntity order); //에러 발생

    /** 주문 삭제 */
//    void deleteOrder(Long orderId);
}