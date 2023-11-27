package seproject.yudelivery.entity;

import jakarta.persistence.*;

public class RiderEntity {

    @Id @GeneratedValue
    @Column(name = "rider_id")
    private Long id;

    @Column(name = "delivery_status")
    private String delivery_status;

    @Column(name = "delivery_address1")
    private String delivery_address1;

    @Column(name = "delivery_address2")
    private String delivery_address2;

    @Column(name = "delivery_address3")
    private String delivery_address3;

    @OneToOne
    @JoinColumn(name = "phone")
    private String phone;
}
