package seproject.yudelivery.dto;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
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
