package seproject.yudelivery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class FoodEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    @Column(name = "food_name")
    private String food_name;

    @Column(name = "food_price")
    private int food_price;

    @Column(name = "food_info")
    private String food_info;

    @Column(name = "food_size")
    private int food_size;
}
