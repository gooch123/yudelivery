package seproject.yudelivery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Currency;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class BasketFoodEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bakset_food_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "food_id")
    private FoodEntity food;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bakset_id")
    private BasketEntity basket;

    private int food_quantity;

    protected BasketFoodEntity(){

    }

    public BasketFoodEntity(FoodEntity food, BasketEntity basket, int food_quantity) {
        this.food = food;
        this.basket = basket;
        this.food_quantity = food_quantity;
    }

    public int getTotalPrice(){
        return food_quantity * food.getFood_price();
    }

    public void changeFoodQuantity(int quantity){
        food_quantity += quantity;
    }
}
