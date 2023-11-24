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
@Builder
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

    @Column(name = "open_time")
    private Date open_time;

    @Column(name = "close_time")
    private Date close_time;
    @Column(name = "deliver_time")
    private Date deliver_time;
    @Column(name = "store_info")
    private String store_info;
    @Column(name = "sales")
    private int sales;
}
