package seproject.yudelivery.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seproject.yudelivery.dto.BasketDTO;
import seproject.yudelivery.entity.BasketEntity;
import seproject.yudelivery.entity.BasketFoodEntity;
import seproject.yudelivery.entity.FoodEntity;
import seproject.yudelivery.entity.StoreEntity;
import seproject.yudelivery.repository.BasketRepository;
import seproject.yudelivery.repository.FoodRepository;
import seproject.yudelivery.repository.StoreRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasketService {

    private final BasketRepository basketRepository;
    private final FoodRepository foodRepository;
    private final StoreRepository storeRepository;

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

    @Transactional
    public void updateBasketFoodQuantity(Long basketFoodId, int quantity){
        basketRepository.updateFoodQuantityToBasket(basketFoodId, quantity);
        if(basketRepository.getFoodQuantity(basketFoodId) <= 0)
            basketRepository.cancelFood(basketFoodId);
    }

    @Transactional
    public void addFoodToBasket(Long foodId, int quantity, Long userId){
        FoodEntity food = foodRepository.findById(foodId).get();
        Long storeId = food.getStore().getId();
        StoreEntity store = storeRepository.findStore(storeId);
        BasketEntity basket = basketRepository.findBasket(userId);
        BasketFoodEntity basketFood = new BasketFoodEntity(food, basket, quantity);
        basketRepository.addFood(basketFood,userId,store);
    }

    @Transactional
    public void cancelFood(Long basketFoodId){
        basketRepository.cancelFood(basketFoodId);
    }

    public int getTotalPrice(Long userId){
        int totalPrice = 0;
        List<BasketFoodEntity> basketFood = basketRepository.findBasketFood(userId);
        for (BasketFoodEntity basketFoodEntity : basketFood) {
            totalPrice += basketFoodEntity.getTotalPrice();
        }
        return totalPrice;
    }

    public String getBasketStoreName(Long userId){
        BasketEntity basket = basketRepository.findBasket(userId);
        if(basket.getStore() == null)
            return "비어있음";
        else
            return basket.getStore().getStore_name();
    }


}
