package seproject.yudelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seproject.yudelivery.dto.OrderStatus;
import seproject.yudelivery.entity.CustomerEntity;
import seproject.yudelivery.entity.OrderEntity;
import seproject.yudelivery.entity.StoreEntity;

import java.util.*;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    /** 사용자 주문 조회 */
    List<OrderEntity> findAllByCustomer_Id(Long customerId);

    /**
     * 사용자와 주문상태에 따른 주문 조회
     */
    List<OrderEntity> findAllByCustomer_IdAndStatus(Long customerId, OrderStatus status);

    /**
     * 가게에 들어온 주문 조회
     */
    List<OrderEntity> findAllByStore_Id(Long storeId);

    List<OrderEntity> findAllByStore_IdAndStatus(Long storeId,OrderStatus status);

    /** 상세 주문 조회 */
    Optional<OrderEntity> findById(Long id);

    /** 주문 생성 */
//    void saveNewOrder(OrderEntity order); //에러 발생

    /** 주문 수정 */
//    void updateOrder(OrderEntity order); //에러 발생

    /** 주문 삭제 */
//    void deleteOrder(Long orderId);
}