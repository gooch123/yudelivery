package seproject.yudelivery.entity;

import jakarta.persistence.*;
import lombok.*;
import seproject.yudelivery.dto.StoreDTO;

import java.sql.Date;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class StoreEntity {
    @Id @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @PrimaryKeyJoinColumn(name = "user_id")
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
