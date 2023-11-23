package seproject.yudelivery.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class ReviewEntity {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @Column(name = "review_content")
    private String review_content;

    @Column(name = "review_starpoint")
    private Double review_starpoint;

    @Column(name = "comment")
    private String comment;


}
