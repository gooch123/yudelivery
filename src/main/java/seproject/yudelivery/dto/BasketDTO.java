package seproject.yudelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BasketDTO {

    private String foodName;
    private int foodQuantity;
    private int foodPrice;

    @Override
    public String toString() {
        return "BasketDTO{" +
                "foodName='" + foodName + '\'' +
                ", foodQuantity=" + foodQuantity +
                ", foodPrice=" + foodPrice +
                '}';
    }
}
