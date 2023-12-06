package seproject.yudelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import seproject.yudelivery.entity.*;

import java.util.Optional;

@AllArgsConstructor
@Getter
public class ReviewDTO {
    private StoreEntity store;
    private UserEntity customer;
    //private OrderEntity order;
    private String review_content;
    private Double review_starpoint;
    private String comment;

    public ReviewEntity toEntity() {
        return ReviewEntity.builder()
                .store(this.store)
                .customer(this.customer)
                //.order(this.order)
                .review_content(this.review_content)
                .review_starpoint(this.review_starpoint)
                .comment(this.comment)
                .build();
    }
}
