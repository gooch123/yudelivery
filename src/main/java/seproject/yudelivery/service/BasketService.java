package seproject.yudelivery.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seproject.yudelivery.dto.BasketDTO;
import seproject.yudelivery.dto.OrderForm;
import seproject.yudelivery.entity.*;
import seproject.yudelivery.repository.BasketRepository;
import seproject.yudelivery.repository.FoodRepository;
import seproject.yudelivery.repository.StoreRepository;
import seproject.yudelivery.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasketService {

    private final BasketRepository basketRepository;
    private final FoodRepository foodRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    /**
     * @return 특정 유저의 장바구니 목록
     */
    public List<BasketDTO> getBasketList(Long userId){
        BasketEntity basket = basketRepository.findBasket(userId);
        List<BasketFoodEntity> basketFood = basketRepository.findBasketFood(basket.getId());
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
    public void addFoodToBasket(Long foodId, int quantity, Long userId){
        FoodEntity food = foodRepository.findById(foodId).get();
        Long storeId = food.getStore().getId();
        StoreEntity store = storeRepository.findStoreById(storeId);
        BasketEntity basket = basketRepository.findBasket(userId);
        BasketFoodEntity basketFood = new BasketFoodEntity(food, basket, quantity);
        try {
            basketRepository.addFood(basketFood,userId,store);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
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

    public OrderForm basketToOrder(Long userId){
        StoreEntity store = basketRepository.findBasket(userId).getStore();
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 없습니다."));
        return new OrderForm((CustomerEntity) userEntity,store);
    }

    /**
     * 장바구니 초기화
     */
    public void clearBasket(Long userId) {
        basketRepository.clear(userId);
    }
}
