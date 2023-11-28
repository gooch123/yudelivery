package seproject.yudelivery.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seproject.yudelivery.entity.FoodEntity;
import seproject.yudelivery.entity.OrderEntity;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class OrderFoodEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_food_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "food_id")
    private FoodEntity food;

    @Column(name = "quantity")
    private int quantity;

    public OrderFoodEntity(OrderEntity order, FoodEntity food, int quantity) {
        this.order = order;
        this.food = food;
        this.quantity = quantity;
    }

    public int foodsPrice(){
        return food.getFood_price() * quantity;
    }
}
