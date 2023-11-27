package seproject.yudelivery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    public OrderEntity() {}

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long order_id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private StoreEntity store_id;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private FoodEntity food_id;

    // user
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer_id;

    @Column(name = "order_time")
    private Date order_time;

}
