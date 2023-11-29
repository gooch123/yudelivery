package seproject.yudelivery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seproject.yudelivery.dto.OrderStatus;

import java.sql.Date;

@Entity
@Getter
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

    @Column(name = "total_price")
    private int totalPrice;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    public OrderEntity() {
    }

    public OrderEntity(StoreEntity store, UserEntity customer, Date order_time, int totalPrice, OrderStatus status) {
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
