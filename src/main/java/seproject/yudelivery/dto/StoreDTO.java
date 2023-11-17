package seproject.yudelivery.dto;

import lombok.AllArgsConstructor;
import seproject.yudelivery.entity.StoreEntity;
import seproject.yudelivery.entity.UserEntity;

import java.sql.Date;

@AllArgsConstructor
public class StoreDTO {
    private Long store_id;
    private UserEntity user;
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

    public StoreEntity toEntity() {
        return new StoreEntity(store_id, user,store_name, address1, address2, address3, category, phone, open_time, close_time, deliver_time, store_info,sales);
    }
}
