package seproject.yudelivery.dto;

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
    private String open_time;
    private String close_time;
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
                .open_time(Time.valueOf(this.open_time))
                .close_time(Time.valueOf(this.close_time))
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
                .open_time(getOpenTimeAsSqlTime())
                .close_time(getCloseTimeAsSqlTime())
                .deliver_time(this.deliver_time)
                .store_info(this.store_info)
                .sales(0)
                .build();
    }

    public Time getOpenTimeAsSqlTime() {
        if (this.open_time == null || this.open_time.trim().isEmpty()) {
            return null;
        }
        return Time.valueOf(this.open_time);
    }

    public Time getCloseTimeAsSqlTime() {
        if (this.close_time == null || this.close_time.trim().isEmpty()) {
            return null;
        }
        return Time.valueOf(this.close_time);
    }
}
