package seproject.yudelivery.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seproject.yudelivery.dto.OrderStatus;
import seproject.yudelivery.dto.OrderViewDTO;
import seproject.yudelivery.dto.OrderFoodDTO;
import seproject.yudelivery.dto.OrderViewToStoreDTO;
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

    /**
     * 주문을 생성
     * @return 생성한 주문 ID
     */
    public OrderEntity createOrder(Long userId){

        List<BasketFoodEntity> basketFoods = basketRepository.findBasketFood(userId); // 장바구니에 담긴 음식 찾기
        BasketEntity basket = basketRepository.findBasket(userId); // 유저의 장바구니 찾기
        StoreEntity store = basket.getStore();
        CustomerEntity customer = basket.getCustomer();
        Date date = new Date();
        long now_date = date.getTime();

        int totalPrice = 0;
        for (BasketFoodEntity basketFood : basketFoods) {
            totalPrice += basketFood.getTotalPrice();
        }

        OrderEntity order = new OrderEntity(store, customer, new java.sql.Date(now_date),totalPrice, OrderStatus.WAIT);
        orderRepository.save(order);
        for (BasketFoodEntity basketFood : basketFoods) {
            OrderFoodEntity orderFoodEntity = new OrderFoodEntity(order, basketFood.getFood(), basketFood.getFood_quantity());
            orderFoodRepository.save(orderFoodEntity);
        }

        /** 주문 후, 장바구니 비우기 */
        basketRepository.clear(userId);
        return order;
    }

    // 사용자 주문 조회 - 완료된 주문
    public List<OrderViewDTO> getCompleteOrderViewList(Long customerId){
        List<OrderEntity> orders = orderRepository.findAllByCustomer_IdAndStatus(customerId,OrderStatus.COMPLETE);
        List<OrderViewDTO> orderViewDTOList = getOrderViewDTOList(orders,OrderStatus.COMPLETE);
        return orderViewDTOList;
    }

    // 사용자 주문 조회 - 대기 주문
    public List<OrderViewDTO> getWaitOrderViewList(Long customerId){
        List<OrderEntity> orders = orderRepository.findAllByCustomer_IdAndStatus(customerId,OrderStatus.WAIT);
        List<OrderViewDTO> orderViewDTOList = getOrderViewDTOList(orders,OrderStatus.WAIT);
        return orderViewDTOList;
    }

    // 사용자 주문 조회 - 배달 중 주문
    public List<OrderViewDTO> getDeliveringOrderViewList(Long customerId){
        List<OrderEntity> orders = orderRepository.findAllByCustomer_IdAndStatus(customerId,OrderStatus.DELIVERING);
        List<OrderViewDTO> orderViewDTOList = getOrderViewDTOList(orders,OrderStatus.DELIVERING);
        return orderViewDTOList;
    }

    // 사용자 주문 조회 - 조리 중 주문
    public List<OrderViewDTO> getCookingOrderViewList(Long customerId){
        List<OrderEntity> orders = orderRepository.findAllByCustomer_IdAndStatus(customerId,OrderStatus.COOKING);
        List<OrderViewDTO> orderViewDTOList = getOrderViewDTOList(orders,OrderStatus.COOKING);
        return orderViewDTOList;
    }

    private List<OrderViewDTO> getOrderViewDTOList(List<OrderEntity> orders, OrderStatus status) {
        String statusStr = null;
        if(status == OrderStatus.WAIT)
            statusStr = "접수 대기";
        else if (status == OrderStatus.COOKING)
            statusStr = "조리 중";
        else if(status == OrderStatus.DELIVERING)
            statusStr = "배달 중";
        else if (status == OrderStatus.COMPLETE)
            statusStr = "배달 완료";
        List<OrderViewDTO> orderViewDTOList = new ArrayList<>();
        for (OrderEntity order : orders) {
            orderViewDTOList.add(new OrderViewDTO(
                    order.getId(),
                    order.getStore().getStore_name(),
                    order.getOrder_time(),
                    order.getTotalPrice(),
                    statusStr));
        }
        return orderViewDTOList;
    }

    /**
     * 가게에 도착한 대기중 주문들 확인
     */
    public List<OrderViewToStoreDTO> getWaitingOrderDTOtoStore(Long storeId){
        List<OrderEntity> orderList = orderRepository.findAllByStore_IdAndStatus(storeId, OrderStatus.WAIT);
        List<OrderViewToStoreDTO> DTOList = new ArrayList<>();
        for (OrderEntity order : orderList) {
            DTOList.add(new OrderViewToStoreDTO(
                    order.getId(),
                    order.getCustomer().getNickname(),
                    order.getOrder_time(),
                    order.getCustomer().getCustomer_address()
            ));
        }
        return DTOList;
    }

    /**
     * 가게의 모든 주문 확인
     */
    public List<OrderViewDTO> getOrderListToStore(Long storeId){
        List<OrderEntity> orders = orderRepository.findAllByStore_Id(storeId);
        List<OrderViewDTO> orderViewDTOList = getOrderViewDTOList(orders,OrderStatus.COOKING);
        return orderViewDTOList;
    }

    //주문 거절
    public void rejectOrder(Long orderId){
        OrderEntity order = orderRepository.findById(orderId).orElse(null);
        order.changeStatus(OrderStatus.CANCEL);
    }

    //주문 수락
    public void acceptOrder(Long orderId){
        OrderEntity order = orderRepository.findById(orderId).orElse(null);
        order.changeStatus(OrderStatus.COOKING);
    }

    //주문 배달 중
    public void deliverOrder(Long orderId){
        OrderEntity order = orderRepository.findById(orderId).orElse(null);
        order.changeStatus(OrderStatus.DELIVERING);
    }

    //배달 완료
    public void completeOrder(Long orderId){
        OrderEntity order = orderRepository.findById(orderId).orElse(null);
        order.changeStatus(OrderStatus.COMPLETE);
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