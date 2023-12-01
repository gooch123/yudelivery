package seproject.yudelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class OrderViewToStoreDTO {

    private Long id;
    private String nickName;
    private Date date;
    private String address;

}
