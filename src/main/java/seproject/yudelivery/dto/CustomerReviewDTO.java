package seproject.yudelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomerReviewDTO {

    private Long id;
    private Long storeId;
    private String storeName;
    private String content;
    private Double starPoint;

}
