package seproject.yudelivery.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seproject.yudelivery.dto.BasketDTO;
import seproject.yudelivery.dto.OrderDTO;
import seproject.yudelivery.entity.CustomerEntity;
import seproject.yudelivery.entity.OrderEntity;
import seproject.yudelivery.repository.OrderRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BasketService basketService; // 장바구니 기능 사용

    public void createOrder(Long userId){
        List<BasketDTO> basketItems = basketService.getBasketList(userId);

        if(basketItems.isEmpty()){
            throw new IllegalStateException("빈 장바구니");
        }

        /** 주문 생성, BasketDTO를 orderEntity로 변환하는 함수 */
        //OrderEntity order = convertBasketToOrder(userId, basketItems);
        //orderRepository.saveNewOrder(order);

        /** 주문 후, 장바구니 비우기 */
        //basketService.clearBasket(userId);

    }

    // 사용자 주문 조회
    public List<OrderDTO> getOrdersByUserId(Long customerId){
        List<OrderEntity> orders = orderRepository.findAllByCustomer_Id(customerId);
        //return convertOrderListToDTO(orders);
        return null; // 임시 (지우기)
    }

    //사용자 주문의 음식들 조회

    // 상세 주문 조회
    public OrderDTO getOrderDetail(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다."));

        //return convertOrderToDTO(order);
        return null; // 임시(지우기)
    }

    // 주소 변경
//    public void updateOrderAddress(Long orderId, String newAddress){
//        OrderEntity order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다."));
//        //order.setDeliveryAddress(newAddress);
//        orderRepository.updateOrder(order); //에러 발생
//    }

    // 주문 삭제
    public void deleteOrder(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId).
                orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다"));
        orderRepository.delete(order);
    }

/*    private OrderEntity convertBasketToOrder(Long userId, List<BasketDTO> basketItems) {
        // BasketDTO를 이용, OrderEntity 생성


        return order; //
    }

    private OrderDTO convertOrderToDTO(OrderEntity order){
        // OrderEntity를 이용, OrderDTO를 생성
        return orderDTO;
    }

    private List<OrderDTO> convertOrderListToDTO(List<OrderEntity> orders) {
        // OrderEntity를 이용, OrderDTO의 리스트 생성
        return orderDTOList;
    }*/
}
