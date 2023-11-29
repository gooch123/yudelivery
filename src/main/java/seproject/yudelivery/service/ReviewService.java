package seproject.yudelivery.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seproject.yudelivery.dto.CustomerReviewDTO;
import seproject.yudelivery.dto.ReviewDTO;
import seproject.yudelivery.entity.CustomerEntity;
import seproject.yudelivery.entity.ReviewEntity;
import seproject.yudelivery.entity.StoreEntity;
import seproject.yudelivery.entity.UserEntity;
import seproject.yudelivery.repository.CustomerRepository;
import seproject.yudelivery.repository.ReviewRepository;
import seproject.yudelivery.repository.StoreRepository;
import seproject.yudelivery.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final UserRepository customerRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, StoreRepository storeRepository, UserRepository customerRepository) {
        this.reviewRepository = reviewRepository;
        this.storeRepository = storeRepository;
        this.customerRepository = customerRepository;

    }

    public ReviewEntity createReview(Long storeId, Long customerId, String content, Double starpoint) {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setReview_content(content);
        reviewEntity.setReview_starpoint(starpoint);
        reviewEntity.setComment(null);

        if (storeId != null) {
            StoreEntity storeEntity = storeRepository.findStoreById(storeId);
            if (storeEntity == null) {
                throw new EntityNotFoundException("Store not found");
            }
            reviewEntity.setStore(storeEntity);
        }
        if (customerId != null) {
            Optional<UserEntity> customerOptional = customerRepository.findById(customerId);
            if (customerOptional.isPresent()) {
                UserEntity customerEntity = customerOptional.get();
                reviewEntity.setCustomer(customerEntity);
            }
        }

        return reviewRepository.save(reviewEntity);
    }

    public ReviewEntity updateReview(Long reviewId, String content, Double starpoint) {
        ReviewEntity existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        existingReview.setReview_content(content);
        existingReview.setReview_starpoint(starpoint);

        return reviewRepository.save(existingReview);
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public ReviewEntity updateComment(Long reviewId, String comment) {
        ReviewEntity existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        existingReview.setComment(comment);

        return reviewRepository.save(existingReview);
    }

    public ReviewEntity deleteComment(Long reviewId) {
        ReviewEntity existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found"));

        existingReview.setComment(null);

        return reviewRepository.save(existingReview);
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
                    reviewEntity.getStore().getId(),
                    reviewEntity.getStore().getStore_name(),
                    reviewEntity.getReview_content(),
                    reviewEntity.getReview_starpoint()
            ));
        }
        return reviewDTOList;
    }

}
