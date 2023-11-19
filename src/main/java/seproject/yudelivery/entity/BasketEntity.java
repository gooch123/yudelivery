package seproject.yudelivery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter @Setter
public class BasketEntity {

    @Id @GeneratedValue
    @Column(name = "basket_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "id")
    private CustomerEntity customer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    @OneToMany(mappedBy = "basket")
    private List<BasketFoodEntity> basketFood;

    public int getBasketPrice(){
        int sum = 0;
        for (BasketFoodEntity basketFoods : basketFood) {
            sum += basketFoods.getTotalPrice();
        }
        return sum;
    }

}
