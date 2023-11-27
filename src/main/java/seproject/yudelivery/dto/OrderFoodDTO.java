package seproject.yudelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderFoodDTO {

    private Long foodId;
    private String foodName;
    private int quantity;

    @Override
    public String toString() {
        return "OrderFoodDTO{" +
                "foodName='" + foodName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
