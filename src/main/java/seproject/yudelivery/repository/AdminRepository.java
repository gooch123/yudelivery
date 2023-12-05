package seproject.yudelivery.repository;

import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import seproject.yudelivery.entity.AdminEntity;
import seproject.yudelivery.entity.ReviewEntity;
import seproject.yudelivery.entity.UserEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AdminRepository {
    private final EntityManager em;

    public List<AdminEntity> findAllReview() {
        return em.createQuery("select s from AdminEntity s", AdminEntity.class)
                 .getResultList();
    }

    public List<AdminEntity> getAllBadReview() {
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

            // review에 욕설이 포함되어 있는지 확인
            if(content.contains("바보") || content.contains("멍청이"))
                ;
            else
                continue;

            // reviewEntity -> adminEntity로 변환
            adminEntity = new AdminEntity(id, content);
            adminEntities.add(adminEntity);
        }

        return adminEntities;
    }

    public ReviewEntity findReviewById(Long id) {
        return em.find(ReviewEntity.class, id);
    }

    public AdminEntity findAdminById(Long id) {
        return em.find(AdminEntity.class, id);
    }

    @Transactional
    public void saveNewReview(AdminEntity review) {
        em.persist(review);
    }

    public void deleteReviewById(Long id) {
        ReviewEntity reviewEntity = findReviewById(id);
        AdminEntity adminEntity = findAdminById(id);
        if (reviewEntity != null) {
            em.remove(reviewEntity);
            em.remove(adminEntity);
        } else {
            log.info("error : no ID for delete review.");
        }
    }

    public void ignoreReviewById(Long id) {
        AdminEntity adminEntity = findAdminById(id);
        if(adminEntity != null) {
            em.remove(adminEntity);
        } else {
            log.info("error : no ID for ignore review.");
        }
    }

    public List<UserEntity> getAllUsers() {
        return em.createQuery("select s from UserEntity s", UserEntity.class)
                .getResultList();
    }

    public UserEntity findUserById(Long id) {
        return em.find(UserEntity.class, id);
    }

    public void banUserById(Long id, String banned_reason) {
        UserEntity userEntity = findUserById(id);
        if (userEntity != null) {
            userEntity.setBanned(true);
            userEntity.setBanned_reason(banned_reason);
        } else {
            log.info("error : no ID for delete user.");
        }
    }
}
