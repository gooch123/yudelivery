package seproject.yudelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class WishListDTO {

    private Long wishListId;
    private String storeName;
    private Long storeId;
    private String storeInfo;

}
