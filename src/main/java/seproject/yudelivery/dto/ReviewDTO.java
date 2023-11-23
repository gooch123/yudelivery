package seproject.yudelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import seproject.yudelivery.entity.CustomerEntity;
import seproject.yudelivery.entity.ReviewEntity;
import seproject.yudelivery.entity.StoreEntity;
import seproject.yudelivery.repository.StoreRepository;

@AllArgsConstructor
@Getter
public class ReviewDTO {
    private Long id;
    private Long store_id;
    private Long customer_id;
    private String review_content;
    private Double review_starpoint;
    private String comment;
    private final StoreRepository storeRepository;

    public ReviewEntity toEntitiy(){
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setId(this.id);
        reviewEntity.setReview_content(this.review_content);
        reviewEntity.setReview_starpoint(this.review_starpoint);
        reviewEntity.setComment(this.comment);

        StoreEntity storeEntity = storeRepository.findStore(this.store_id);
        reviewEntity.setStore(storeEntity);

        //customer해야함

        return reviewEntity;
    }
}
