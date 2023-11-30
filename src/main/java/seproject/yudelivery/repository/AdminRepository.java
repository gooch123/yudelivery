package seproject.yudelivery.repository;

import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import seproject.yudelivery.entity.AdminEntity;

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
    public AdminEntity findReviewById(Long reportedId) {
        return em.find(AdminEntity.class, reportedId);
    }

    @Transactional
    public void saveNewReview(AdminEntity review) { em.persist(review); }

    public void deleteReviewById(Long reportedId){
        AdminEntity review = findReviewById(reportedId);
        if(review != null) {
            em.remove(review);
        }
        else {
            log.info("error : no review id.");
        }
    }
}
