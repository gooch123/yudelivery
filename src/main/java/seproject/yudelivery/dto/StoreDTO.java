package seproject.yudelivery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import seproject.yudelivery.entity.StoreEntity;
import seproject.yudelivery.entity.UserEntity;


import java.sql.Time;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class StoreDTO {
    private Long store_id;
    private UserEntity user;
    private String store_name;
    private String address1;
    private String address2;
    private String address3;
    private String category;
    private String phone;
    private Time open_time;
    private Time close_time;
    private int deliver_time;
    private String store_info;
    private int sales;

    public StoreEntity toEntity() {
        return StoreEntity.builder()
                .id(this.store_id)
                .user(this.user)
                .store_name(this.store_name)
                .address1(this.address1)
                .address2(this.address2)
                .address3(this.address3)
                .category(this.category)
                .phone(this.phone)
                .open_time(this.open_time)
                .close_time(this.close_time)
                .deliver_time(this.deliver_time)
                .store_info(this.store_info)
                .sales(this.sales)
                .build();
    }
    public StoreEntity createStore(){
        return StoreEntity.builder()
                .user(this.user)
                .store_name(this.store_name)
                .address1(this.address1)
                .address2(this.address2)
                .address3(this.address3)
                .category(this.category)
                .phone(this.phone)
                .open_time(this.open_time)
                .close_time(this.close_time)
                .deliver_time(this.deliver_time)
                .store_info(this.store_info)
                .sales(0)
                .build();
    }
}
