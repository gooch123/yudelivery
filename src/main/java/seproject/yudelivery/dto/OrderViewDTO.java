package seproject.yudelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.sql.Date;

@AllArgsConstructor
@Getter
@ToString
public class OrderViewDTO {

    private Long orderId;
    private String storeName;
    private Date order_time;
    private int totalPrice;
    private String status;

}
