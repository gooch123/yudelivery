package seproject.yudelivery.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seproject.yudelivery.dto.OrderViewDTO;
import seproject.yudelivery.dto.OrderFoodDTO;
import seproject.yudelivery.dto.OrderForm;
import seproject.yudelivery.entity.*;
import seproject.yudelivery.repository.BasketRepository;
import seproject.yudelivery.repository.OrderFoodRepository;
import seproject.yudelivery.repository.OrderRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderFoodRepository orderFoodRepository;
    private final BasketRepository basketRepository; //장바구니 기능 사용

    public OrderEntity createOrder(Long userId){

        List<BasketFoodEntity> basketFoods = basketRepository.findBasketFood(userId); // 장바구니에 담긴 음식 찾기
        BasketEntity basket = basketRepository.findBasket(userId); // 유저의 장바구니 찾기
        StoreEntity store = basket.getStore();
        UserEntity customer = basket.getCustomer();
        Date date = new Date();
        long now_date = date.getTime();

        int totalPrice = 0;
        for (BasketFoodEntity basketFood : basketFoods) {
            totalPrice += basketFood.getTotalPrice();
        }

        OrderEntity order = new OrderEntity(store, customer, new java.sql.Date(now_date),totalPrice);
        orderRepository.save(order);
        for (BasketFoodEntity basketFood : basketFoods) {
            OrderFoodEntity orderFoodEntity = new OrderFoodEntity(order, basketFood.getFood(), basketFood.getFood_quantity());
            orderFoodRepository.save(orderFoodEntity);
        }

        /** 주문 후, 장바구니 비우기 */
        basketRepository.clear(userId);
        return order;
    }

    // 사용자 주문 조회
    public List<OrderViewDTO> getOrderViewList(Long customerId){
        List<OrderEntity> orders = orderRepository.findAllByCustomer_Id(customerId);
        List<OrderViewDTO> orderViewDTOList = new ArrayList<>();
        for (OrderEntity order : orders) {
            orderViewDTOList.add(new OrderViewDTO(
                    order.getId(),
                    order.getStore().getStore_name(),
                    order.getOrder_time(),
                    order.getTotalPrice()));
        }
        return orderViewDTOList;
    }

    //사용자 주문의 음식들 조회
    public List<OrderFoodDTO> getOrderFoods(Long orderId){
        List<OrderFoodEntity> list = orderFoodRepository.findAllByOrder_Id(orderId);
        List<OrderFoodDTO> orderFoodDTOList = new ArrayList<>();
        for (OrderFoodEntity orderFoodEntity : list) {
            orderFoodDTOList.add(new OrderFoodDTO(
                    orderFoodEntity.getFood().getId(),
                    orderFoodEntity.getFood().getFood_name(),
                    orderFoodEntity.foodsPrice(),
                    orderFoodEntity.getQuantity()
            ));
        }
        return orderFoodDTOList;
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

//   private OrderEntity convertBasketToOrder(Long userId, List<BasketDTO> basketItems) {
//        // BasketDTO를 이용, OrderEntity 생성
//        new OrderEntity();
//
//        return order; //
//    }

//    private OrderDTO convertOrderToDTO(OrderEntity order){
//        // OrderEntity를 이용, OrderDTO를 생성
//        return orderDTO;
//    }
//
//    private List<OrderDTO> convertOrderListToDTO(List<OrderEntity> orders) {
//        // OrderEntity를 이용, OrderDTO의 리스트 생성
//        return orderDTOList;
//
}