package seproject.yudelivery.entity;

import jakarta.persistence.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class RiderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private UserEntity customer;

    public RiderEntity() {
    }

    public RiderEntity(String deliveryStatus, String deliveryAddress1, String deliveryAddress2, String deliveryAddress3, String phone, UserEntity customer) {
        this.deliveryStatus = deliveryStatus;
        this.deliveryAddress1 = deliveryAddress1;
        this.deliveryAddress2 = deliveryAddress2;
        this.deliveryAddress3 = deliveryAddress3;
        this.phone = phone;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveryAddress1() {
        return deliveryAddress1;
    }

    public void setDeliveryAddress1(String deliveryAddress1) {
        this.deliveryAddress1 = deliveryAddress1;
    }

    public String getDeliveryAddress2() {
        return deliveryAddress2;
    }

    public void setDeliveryAddress2(String deliveryAddress2) {
        this.deliveryAddress2 = deliveryAddress2;
    }

    public String getDeliveryAddress3() {
        return deliveryAddress3;
    }

    public void setDeliveryAddress3(String deliveryAddress3) {
        this.deliveryAddress3 = deliveryAddress3;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public UserEntity getCustomer() {
        return customer;
    }

    public void setCustomer(UserEntity customer) {
        this.customer = customer;
    }

    public void markAsDelivered() {
        this.deliveryStatus = "DELIVERED";
    }
}
