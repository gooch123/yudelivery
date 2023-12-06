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
    private String food_price;
    private String food_info;
    private int food_size;

    public FoodEntity toEntity() {
        if(food_price.equals(""))
            food_price = "0";
        int price = Integer.parseInt(food_price);
        return FoodEntity.builder()
                .id(this.id)
                .store(this.store)
                .food_name(this.food_name)
                .food_price(price)
                .food_info(this.food_info)
                .food_size(this.food_size)
                .build();
    }
}
