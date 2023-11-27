package seproject.yudelivery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Builder
@AllArgsConstructor
public class OrderEntity {
    public OrderEntity() {}

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

}
