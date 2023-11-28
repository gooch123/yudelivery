package seproject.yudelivery.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seproject.yudelivery.dto.FoodDTO;
import seproject.yudelivery.entity.FoodEntity;
import seproject.yudelivery.entity.StoreEntity;
import seproject.yudelivery.repository.FoodRepository;
import seproject.yudelivery.repository.StoreRepository;

import java.util.Optional;

@Service
public class FoodService {
    private final FoodRepository foodRepository;
    private final StoreRepository storeRepository;

    @Autowired
    public FoodService(FoodRepository foodRepository, StoreRepository storeRepository) {
        this.foodRepository = foodRepository;
        this.storeRepository = storeRepository;
    }

    public FoodEntity createFood(FoodDTO dto) {
        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setFood_name(dto.getFood_name());
        foodEntity.setFood_price(dto.getFood_price());
        foodEntity.setFood_info(dto.getFood_info());
        foodEntity.setFood_size(dto.getFood_size());

        if (dto.getStore().getId() != null) {
            StoreEntity storeEntity = storeRepository.findStoreById(dto.getStore().getId());
            if (storeEntity == null) {
                throw new EntityNotFoundException("Store not found");
            }
            foodEntity.setStore(storeEntity);
        }

        return foodRepository.save(foodEntity);
    }

    public FoodEntity updateFood(Long foodId, FoodDTO dto) {
        FoodEntity existingFood = foodRepository.findById(foodId)
                .orElseThrow(() -> new EntityNotFoundException("Food not found"));

        existingFood.setFood_name(dto.getFood_name());
        existingFood.setFood_price(dto.getFood_price());
        existingFood.setFood_info(dto.getFood_info());
        existingFood.setFood_size(dto.getFood_size());

        return foodRepository.save(existingFood);
    }

    public void deleteFood(Long foodId) {
        foodRepository.deleteById(foodId);
    }

    public FoodEntity getFood(Long foodId) {
        return foodRepository.findById(foodId)
                .orElseThrow(() -> new EntityNotFoundException("Food not found"));
    }

    // 다른 필요한 메서드
}
