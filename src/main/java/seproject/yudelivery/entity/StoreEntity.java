package seproject.yudelivery.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class StoreEntity {
    @Id @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    @OneToOne
    @Column(name = "user_id")
    private UserEntity user;

    @Column(name = "store_name")
    private String store_name;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "address3")
    private String address3;

    @Column(name = "phone")
    private String phone;

    @Column(name = "opening_time")
    private Date opening_time;

    @Column(name = closing_time")
    private Date closing_time;
    @Column(name = "delivery_time")
    private Date delivery_time;
    @Column(name = "store_info")
    private String store_info;
    @Column(name = "sales")
    private int sales;
}
