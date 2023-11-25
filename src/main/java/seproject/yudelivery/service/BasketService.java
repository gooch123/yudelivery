package seproject.yudelivery.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seproject.yudelivery.dto.BasketDTO;
import seproject.yudelivery.entity.BasketEntity;
import seproject.yudelivery.entity.BasketFoodEntity;
import seproject.yudelivery.entity.FoodEntity;
import seproject.yudelivery.repository.BasketRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasketService {

    private final BasketRepository basketRepository;

    public List<BasketDTO> getBasket(Long userId){
        Long basketId = basketRepository.findBasketId(userId);
        List<BasketFoodEntity> basketFood = basketRepository.findBasketFood(basketId);
        List<BasketDTO> basketDTOList = new ArrayList<>();
        for (BasketFoodEntity food_in_basket : basketFood) {
            FoodEntity food = food_in_basket.getFood();
            //entity 에서 바로 얻어오는 방법은 좋은 방법이 아님. 추후 수정 필요
            BasketDTO basketDTO = new BasketDTO(food.getId(),food.getFood_name(), food_in_basket.getFood_quantity(), food.getFood_price());
            basketDTOList.add(basketDTO);
        }

        return basketDTOList;
    }

    @Transactional
    public void updateBasketFoodQuantity(Long basketFoodId, int quantity){
        basketRepository.updateBasket(basketFoodId, quantity);
        if(basketRepository.getFoodQuantity(basketFoodId) <= 0)
            basketRepository.cancelFood(basketFoodId);
    }

}
