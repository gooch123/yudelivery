package seproject.yudelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import seproject.yudelivery.entity.CustomerEntity;
import seproject.yudelivery.entity.FoodEntity;
import seproject.yudelivery.entity.OrderEntity;
import seproject.yudelivery.entity.StoreEntity;

import java.sql.Date;

@AllArgsConstructor
@Getter
@ToString
public class OrderDTO {
    private Long id;
    private StoreEntity store_id;
    private FoodEntity food_id;
    private CustomerEntity customer_id;
    private Date order_time;

    public OrderEntity toEntity() {
        return OrderEntity.builder()
                .order_id(this.id)
                .store_id(this.store_id)
                .food_id(this.food_id)
                .customer_id(this.customer_id)
                .order_time(this.order_time)
                .build();
    }
}
