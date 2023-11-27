package seproject.yudelivery.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Builder
@Getter
@Setter
public class OrderEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    //음식과 주문은 다대다 관계라 orderFoodEntity 를 추가하고 Food 속성을 삭제했습니다

    // user
    @ManyToOne
    @JoinColumn(name = "id")
    private UserEntity customer;

    @Column(name = "order_time")
    private Date order_time;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    public OrderEntity() {
    }

    public OrderEntity(StoreEntity store, UserEntity customer, Date order_time) {
        this.store = store;
        this.customer = customer;
        this.order_time = order_time;
    }

    public OrderEntity(Long id, StoreEntity store, UserEntity customer, Date order_time) {
        this.id = id;
        this.store = store;
        this.customer = customer;
        this.order_time = order_time;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
