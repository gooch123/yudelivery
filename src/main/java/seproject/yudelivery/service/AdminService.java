package seproject.yudelivery.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seproject.yudelivery.entity.AdminEntity;
import seproject.yudelivery.entity.ReviewEntity;
import seproject.yudelivery.repository.AdminRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    private final AdminRepository adminRepository;

    public List<AdminEntity> findAllReview() {
        return adminRepository.findAllReview();
    }
    public ReviewEntity findReviewById(Long reportedId) {
        return adminRepository.findReviewById(reportedId);
    }

    public void deleteReviewById(Long reportedId){
        adminRepository.deleteReviewById(reportedId);
    }
}