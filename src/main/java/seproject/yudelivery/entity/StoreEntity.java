package seproject.yudelivery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import seproject.yudelivery.dto.StoreDTO;

import java.sql.Date;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class StoreEntity {
    @Id @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "store_name")
    private String store_name;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "address3")
    private String address3;

    @Column(name = "category")
    private String category;

    @Column(name = "phone")
    private String phone;

    @Column(name = "opening_time")
    private Date opening_time;

    @Column(name = "closing_time")
    private Date closing_time;
    @Column(name = "delivery_time")
    private Date delivery_time;
    @Column(name = "store_info")
    private String store_info;
    @Column(name = "sales")
    private int sales;

    public StoreEntity createStore(StoreDTO dto){
        if(dto.getStore_id() != null){ // id 존재하면 생성 불가
            throw new IllegalArgumentException("이미 존재하는 가게입니다.");
        }
        else if(dto.getStore_name() != null) { // 이미 이름이 존재하는 가게이면 생성 불가
            throw new IllegalArgumentException("이미 존재하는 가게입니다.");
        }
        return new StoreEntity(
                dto.getStore_id(),
                user,
                dto.getStore_name(),
                dto.getAddress1(),
                dto.getAddress2(),
                dto.getAddress3(),
                dto.getCategory(),
                dto.getPhone(),
                dto.getOpen_time(),
                dto.getClose_time(),
                dto.getDeliver_time(),
                dto.getStore_info(),
                0);
    }
}
