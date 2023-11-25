package seproject.yudelivery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor
public class WishListEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private UserEntity customer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    public WishListEntity(UserEntity customer, StoreEntity store) {
        this.customer = customer;
        this.store = store;
    }
}
