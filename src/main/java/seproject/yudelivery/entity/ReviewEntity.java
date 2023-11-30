package seproject.yudelivery.entity;

import jakarta.persistence.*;
import lombok.*;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private UserEntity customer;

    /*
    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
     */

    @Column(name = "review_content")
    private String review_content;

    @Column(name = "review_starpoint")
    private Double review_starpoint;

    @Column(name = "comment")
    private String comment;

}
