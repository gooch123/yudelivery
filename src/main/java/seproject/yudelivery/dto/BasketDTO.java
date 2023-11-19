package seproject.yudelivery.dto;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class BasketDTO {

    private String storeName;
    private String foodName;
    private String foodQuantity;
    private int foodPrice;

}
