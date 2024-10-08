package seproject.yudelivery.entity;

import jakarta.persistence.*;
import lombok.*;
import seproject.yudelivery.dto.OrderStatus;

import java.sql.Date;

@Entity
@Getter
@ToString
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
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @Column(name = "order_time")
    private Date order_time;

    @Column(name = "total_price")
    private int totalPrice;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    public OrderEntity() {
    }

    public OrderEntity(StoreEntity store, CustomerEntity customer, Date order_time, int totalPrice, OrderStatus status) {
        this.store = store;
        this.customer = customer;
        this.order_time = order_time;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public void changeStatus(OrderStatus status){
        this.status = status;
    }

}
