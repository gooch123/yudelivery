package seproject.yudelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import seproject.yudelivery.entity.FoodEntity;
import seproject.yudelivery.entity.StoreEntity;
import seproject.yudelivery.repository.StoreRepository;

@AllArgsConstructor
@Getter
public class FoodDTO {
    private Long id;
    private Long store_id;
    private String food_name;
    private int food_price;
    private String food_info;
    private int food_size;

    private final StoreRepository storeRepository;

    public FoodEntity toEntity() {
        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setId(this.id); // Setting foodId if needed
        foodEntity.setFood_name(this.food_name);
        foodEntity.setFood_price(this.food_price);
        foodEntity.setFood_info(this.food_info);
        foodEntity.setFood_size(this.food_size);

        StoreEntity storeEntity = storeRepository.findMyStore(this.store_id);
        foodEntity.setStore(storeEntity);

        return foodEntity;
    }
}
