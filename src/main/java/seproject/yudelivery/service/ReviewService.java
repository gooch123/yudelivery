package seproject.yudelivery.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seproject.yudelivery.dto.CustomerReviewDTO;
import seproject.yudelivery.dto.ReviewDTO;
import seproject.yudelivery.entity.ReviewEntity;
import seproject.yudelivery.entity.StoreEntity;
import seproject.yudelivery.repository.ReviewRepository;
import seproject.yudelivery.repository.StoreRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    //private final CustomerRepository customerRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, StoreRepository storeRepository) {
        this.reviewRepository = reviewRepository;
        this.storeRepository = storeRepository;
        //this.customerRepository = customerRepository;

    }

    public ReviewEntity createReview(ReviewDTO dto) {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setReview_content(dto.getReview_content());
        reviewEntity.setReview_starpoint(dto.getReview_starpoint());
        reviewEntity.setComment(dto.getComment());

        if (dto.getStore_id() != null) {
            StoreEntity storeEntity = storeRepository.findStoreById(dto.getStore_id());
            if (storeEntity == null) {
                throw new EntityNotFoundException("Store not found");
            }
            reviewEntity.setStore(storeEntity);
        }
        /* 나중에 추가해야함
        if (dto.getCustomer_id() != null) {
            StoreEntity storeEntity = storeRepository.findCustomer(dto.getCustomer_id());
            if (customerEntity == null) {
                throw new EntityNotFoundException("customer not found");
            }
            reviewEntity.setCustomer(customerEntity);
        }
         */

        return reviewRepository.save(reviewEntity);
    }

    public ReviewEntity updateFood(Long reviewId, ReviewDTO dto) {
        ReviewEntity existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Food not found"));

        existingReview.setReview_content(dto.getReview_content());
        existingReview.setReview_starpoint(dto.getReview_starpoint());
        existingReview.setComment(dto.getComment());

        return reviewRepository.save(existingReview);
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public ReviewEntity getReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Food not found"));
    }

    // 다른 필요한 메서드

    public List<CustomerReviewDTO> getReviewListByCustomer(Long customerId){
        List<ReviewEntity> review = reviewRepository.findAllByCustomer_Id(customerId);
        List<CustomerReviewDTO> reviewDTOList = new ArrayList<>();
        for (ReviewEntity reviewEntity : review) {
            reviewDTOList.add(new CustomerReviewDTO(
                    reviewEntity.getId(),
                    reviewEntity.getStore().getStore_name(),
                    reviewEntity.getReview_content(),
                    reviewEntity.getReview_starpoint()
            ));
        }
        return reviewDTOList;
    }

}
