package seproject.yudelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import seproject.yudelivery.entity.CustomerEntity;
import seproject.yudelivery.entity.FoodEntity;
import seproject.yudelivery.entity.StoreEntity;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderForm {

    private CustomerEntity customer;
    private StoreEntity store;

}
