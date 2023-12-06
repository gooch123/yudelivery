package seproject.yudelivery.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seproject.yudelivery.dto.AdminDTO;
import seproject.yudelivery.entity.AdminEntity;
import seproject.yudelivery.entity.ReviewEntity;
import seproject.yudelivery.entity.UserEntity;
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
    public List<AdminEntity> getAllBadReview() {
        return adminRepository.getAllBadReview();
    }

    public ReviewEntity findReviewById(Long reportedId) {
        return adminRepository.findReviewById(reportedId);
    }

    public AdminEntity findAdminById(Long reportedId) {
        return adminRepository.findAdminById(reportedId);
    }

    public AdminEntity createAdmin(AdminDTO adminDTO) { //삭제 요청받은 리뷰를 admin엔티티로 보냄
        AdminEntity admin = adminDTO.toEntity();
        adminRepository.saveNewReview(admin);
        return admin;
    }
    public void deleteReviewById(Long reportedId){
        adminRepository.deleteReviewById(reportedId);
    }

    public void ignoreReviewById(Long reportedId) {
        adminRepository.ignoreReviewById(reportedId);
    }

    public List<UserEntity> getAllUsers() {
        return adminRepository.getAllUsers();
    }

    public UserEntity findUserById(Long id) {
        return adminRepository.findUserById(id);
    }

    public void banUserById(Long id, String banned_reason) {
        adminRepository.banUserById(id, banned_reason);
    }
}