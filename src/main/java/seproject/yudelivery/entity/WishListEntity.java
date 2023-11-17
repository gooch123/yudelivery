package seproject.yudelivery.entity;

import jakarta.persistence.*;

import static jakarta.persistence.FetchType.*;

@Entity
public class WishListEntity {

    @Id @GeneratedValue
    @Column(name = "wishlist_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private StoreEntity store;

}
