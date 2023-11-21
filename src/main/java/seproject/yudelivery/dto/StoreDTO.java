package seproject.yudelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.sql.Date;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class StoreDTO {
    private Long store_id;
    private Long user_id;
    private String store_name;
    private String address1;
    private String address2;
    private String address3;
    private String category;
    private String phone;
    private Date open_time;
    private Date close_time;
    private Date deliver_time;
    private String store_info;
    private int sales;
}
