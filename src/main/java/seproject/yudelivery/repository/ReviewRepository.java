package seproject.yudelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seproject.yudelivery.entity.ReviewEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findAllByStoreId(Long storeId);
    Optional<ReviewEntity> findById(Long id);

    default ReviewEntity saveNewReview(ReviewEntity review) {
        return save(review); // 새로운 리뷰 저장
    }

    default void deleteReview(Long id) {
        deleteById(id); // 리뷰 삭제
    }

    default ReviewEntity updateReview(ReviewEntity review) {
        return save(review); // 리뷰 업데이트
    }
}
