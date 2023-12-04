package seproject.yudelivery.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seproject.yudelivery.dto.BasketDTO;
import seproject.yudelivery.entity.*;
import seproject.yudelivery.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BasketService {

    private final BasketRepository basketRepository;
    private final FoodRepository foodRepository;
    private final StoreRepository storeRepository;
    private final OrderFoodRepository orderFoodRepository;
    private final OrderRepository orderRepository;


    /**
     * @return 특정 유저의 장바구니 목록
     */
    public List<BasketDTO> getBasketList(Long userId){
        List<BasketFoodEntity> basketFood = basketRepository.findBasketFood(userId);
        List<BasketDTO> basketDTOList = new ArrayList<>();
        for (BasketFoodEntity food_in_basket : basketFood) {
            FoodEntity food = food_in_basket.getFood();
            BasketDTO basketDTO = new BasketDTO(food_in_basket.getId(),food.getFood_name(), food_in_basket.getFood_quantity(), food.getFood_price());
            basketDTOList.add(basketDTO);
        }

        return basketDTOList;
    }

    /**
     * 장바구니에 담은 음식의 수량 조절, 0이 되면 자동으로 삭제
     */
    @Transactional
    public void updateBasketFoodQuantity(Long basketFoodId, int quantity){
        basketRepository.updateFoodQuantityToBasket(basketFoodId, quantity);
        if(basketRepository.getFoodQuantity(basketFoodId) <= 0)
            basketRepository.cancelFood(basketFoodId);
    }

    /**
     * 장바구니에 음식 담기
     */
    @Transactional
    public void addFoodToBasket(Long foodId, int quantity, Long userId) throws IllegalStateException{
        FoodEntity food = foodRepository.findById(foodId).get();
        Long storeId = food.getStore().getId();
        StoreEntity store = storeRepository.findStoreById(storeId);
        BasketEntity basket = basketRepository.findBasket(userId);
        BasketFoodEntity basketFood = new BasketFoodEntity(food, basket, quantity);
        log.info("basketFood.id = " + basketFood.getFood().getId() +
                "basketFood.id.name" + basketFood.getFood().getFood_name());
        basketRepository.addFood(basketFood,userId,store);
    }

    /**
     * 장바구니의 목록 삭제
     */
    @Transactional
    public void cancelFood(Long basketFoodId){
        basketRepository.cancelFood(basketFoodId);
    }

    /**
     * @return 특정 유저의 장바구니에 있는 품목의 가격 합
     */
    public int getTotalPrice(Long userId){
        int totalPrice = 0;
        List<BasketFoodEntity> basketFood = basketRepository.findBasketFood(userId);
        for (BasketFoodEntity basketFoodEntity : basketFood) {
            totalPrice += basketFoodEntity.getTotalPrice();
        }
        return totalPrice;
    }

    /**
     * @return 유저가 장바구니에 담은 음식의 가게 이름
     */
    public String getBasketStoreName(Long userId){
        BasketEntity basket = basketRepository.findBasket(userId);
        if(basket.getStore() == null)
            return "비어있음";
        else
            return basket.getStore().getStore_name();
    }

    /**
     * 장바구니 초기화
     */
    public void clearBasket(Long userId) {
        basketRepository.clear(userId);
    }

    /**
     * 이전 주문 다시 장바구니에 담기
     */
    @Transactional
    public void reorder (Long orderId) throws IllegalStateException{
        List<OrderFoodEntity> orderFoodList = orderFoodRepository.findAllByOrder_Id(orderId);
        OrderEntity order = orderRepository.findById(orderId).orElse(null);
        for (OrderFoodEntity orderFood : orderFoodList) {
            addFoodToBasket(orderFood.getFood().getId(),orderFood.getQuantity(),order.getCustomer().getId());
        }
    }

    @Transactional
    public void newBasket(BasketEntity basket){
        basketRepository.saveNewBasket(basket);
    }
}
