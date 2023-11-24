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
    private UserEntity customer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private StoreEntity store;

}
