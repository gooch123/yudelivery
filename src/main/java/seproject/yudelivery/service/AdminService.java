package seproject.yudelivery.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seproject.yudelivery.entity.AdminEntity;
import seproject.yudelivery.repository.AdminRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    private final AdminRepository administratorRepository;

    public List<AdminEntity> findAllReportedReview() {
        return administratorRepository.findAllReportedReview();
    }
    public AdminEntity findReportedReviewById(Long reportedId) {
        return administratorRepository.findReportedReviewById(reportedId);
    }

    public void deleteReportedReviewById(Long reportedId){
        administratorRepository.deleteReportedReviewById(reportedId);
    }
}