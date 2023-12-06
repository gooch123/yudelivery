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
    private String  deliver_time;
    private String store_info;
    private int sales;

    public StoreEntity toEntity() {
        String openTimeFormatted;
        String closeTimeFormatted;
        if(this.open_time.length() == 0)
            openTimeFormatted = "00:00:00";
        else
            openTimeFormatted = this.open_time.length() == 5 ? this.open_time + ":00" : this.open_time;
        if(this.close_time.length() == 0)
            closeTimeFormatted = "00:00:00";
        else
            closeTimeFormatted = this.close_time.length() == 5 ? this.close_time + ":00" : this.close_time;
        if(this.deliver_time.equals("")) {
            this.deliver_time = "0";
        }
        int deliver = Integer.parseInt(this.deliver_time);
        return StoreEntity.builder()
                .id(this.store_id)
                .user(this.user)
                .store_name(this.store_name)
                .address1(this.address1)
                .address2(this.address2)
                .address3(this.address3)
                .category(this.category)
                .phone(this.phone)
                .open_time(Time.valueOf(openTimeFormatted))
                .close_time(Time.valueOf(closeTimeFormatted))
                .deliver_time(deliver)
                .store_info(this.store_info)
                .sales(this.sales)
                .build();
    }

    public StoreEntity createStore() {
        String openTimeFormatted;
        String closeTimeFormatted;
        if(this.open_time.length() == 0)
            openTimeFormatted = "00:00:00";
        else
            openTimeFormatted = this.open_time + ":00";
        if(this.close_time.length() == 0)
            closeTimeFormatted = "00:00:00";
        else
            closeTimeFormatted = this.close_time + ":00";
        if(this.deliver_time.equals("")) {
            this.deliver_time = "0";
        }
        int deliver = Integer.parseInt(this.deliver_time);
        return StoreEntity.builder()
                .user(this.user)
                .store_name(this.store_name)
                .address1(this.address1)
                .address2(this.address2)
                .address3(this.address3)
                .category(this.category)
                .phone(this.phone)
                .open_time(Time.valueOf(openTimeFormatted))
                .close_time(Time.valueOf(closeTimeFormatted))
                .deliver_time(deliver)
                .store_info(this.store_info)
                .sales(0)
                .build();
    }
}