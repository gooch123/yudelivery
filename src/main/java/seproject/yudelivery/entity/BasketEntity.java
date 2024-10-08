package seproject.yudelivery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter @Setter
public class BasketEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basket_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    public void clearStore(){
        this.store = null;
    }

}
