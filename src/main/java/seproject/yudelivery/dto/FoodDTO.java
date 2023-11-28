package seproject.yudelivery.dto;

import lombok.*;
import seproject.yudelivery.entity.FoodEntity;
import seproject.yudelivery.entity.StoreEntity;
import seproject.yudelivery.repository.StoreRepository;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@ToString
public class FoodDTO {
    private Long id;
    private StoreEntity store;
    private String food_name;
    private int food_price;
    private String food_info;
    private int food_size;

    public FoodEntity toEntity() {
        return FoodEntity.builder()
                .id(this.id)
                .store(this.store)
                .food_name(this.food_name)
                .food_price(this.food_price)
                .food_info(this.food_info)
                .food_size(this.food_size)
                .build();
    }
}
