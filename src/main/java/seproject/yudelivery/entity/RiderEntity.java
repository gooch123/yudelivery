package seproject.yudelivery.entity;

import jakarta.persistence.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class RiderEntity {

    @Id
    @GeneratedValue
    @Column(name = "rider_id")
    private Long id;

    @Column(name = "delivery_status")
    private String deliveryStatus;

    @Column(name = "delivery_address1")
    private String deliveryAddress1;

    @Column(name = "delivery_address2")
    private String deliveryAddress2;

    @Column(name = "delivery_address3")
    private String deliveryAddress3;

    @Column(name = "phone")
    private String phone;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private UserEntity customer;

}
