package seproject.yudelivery.repository;

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

    public List<AdminEntity> findAllReportedReview() {
        return em.createQuery("select s from AdminEntity s", AdminEntity.class)
                .getResultList();
    }
    public AdminEntity findReportedReviewById(Long reportedId) {
        return em.find(AdminEntity.class, reportedId);
    }

    public void deleteReportedReviewById(Long reportedId){
        AdminEntity reportedReview = findReportedReviewById(reportedId);
        if(reportedReview != null) {
            em.remove(reportedReview);
        }
        else {
            log.info("error : no reported review id.");
        }
    }
}
