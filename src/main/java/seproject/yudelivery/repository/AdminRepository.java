package seproject.yudelivery.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import seproject.yudelivery.entity.AdminEntity;
import seproject.yudelivery.entity.ReviewEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AdminRepository {
    private final EntityManager em;

    public List<AdminEntity> findAllReview() {
        Long id;
        String content;
        ReviewEntity reviewEntity;
        List<ReviewEntity> reviewEntities = em.createQuery("select s from ReviewEntity s", ReviewEntity.class)
                                              .getResultList();
        AdminEntity adminEntity;
        List<AdminEntity> adminEntities = new ArrayList<>();

        // findAllReview 로직
        Iterator<ReviewEntity> iter = reviewEntities.iterator();
        while(iter.hasNext()) {
            // reviewEntity 가져오기
            reviewEntity = iter.next();
            id = reviewEntity.getId();
            content = reviewEntity.getComment();

            // reviewEntity -> adminEntity로 변환
            adminEntity = new AdminEntity(id, content);
            adminEntities.add(adminEntity);
        }

        return adminEntities;
    }
    public ReviewEntity findReviewById(Long id) {
        return em.find(ReviewEntity.class, id);
    }

    public void deleteReviewById(Long id){
        ReviewEntity reviewEntity = findReviewById(id);
        if(reviewEntity != null) {
            em.remove(reviewEntity);
        } else {
            log.info("error : no reviewEntity.");
        }
    }
}
