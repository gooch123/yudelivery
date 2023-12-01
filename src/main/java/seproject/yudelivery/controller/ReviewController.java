package seproject.yudelivery.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import seproject.yudelivery.dto.AdminDTO;
import seproject.yudelivery.dto.UserRole;
import seproject.yudelivery.entity.AdminEntity;
import seproject.yudelivery.entity.ReviewEntity;
import seproject.yudelivery.entity.StoreEntity;
import seproject.yudelivery.entity.UserEntity;
import seproject.yudelivery.repository.AdminRepository;
import seproject.yudelivery.repository.ReviewRepository;
import seproject.yudelivery.service.AdminService;
import seproject.yudelivery.service.ReviewService;
import seproject.yudelivery.service.StoreService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping
public class ReviewController {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private AdminRepository adminRepository;
    private final ReviewService reviewService;
    private final AdminService adminService;

    private final StoreService storeService;

    public ReviewController(ReviewService reviewService, AdminService adminService, StoreService storeService) {
        this.reviewService = reviewService;
        this.adminService = adminService;
        this.storeService = storeService;
    }


    @GetMapping("/store/review")
    public String reviewMain(Model model, RedirectAttributes rttr, @SessionAttribute(name = "user", required = false) UserEntity user){
        if(user == null || user.getRole() != UserRole.STORE){
            return "redirect:/login";
        }
        StoreEntity storeEntity = storeService.getMyStore(user.getId());
        Long storeId = storeEntity.getId();
        if (storeId != null) {
            List<ReviewEntity> reviewList = reviewRepository.findAllByStoreId(storeId);
            if(reviewList.isEmpty()) {
                rttr.addFlashAttribute("msg", "리뷰가 존재하지 않습니다.");
                return "redirect:/store";
            }
            else{
                model.addAttribute("reviewList", reviewList);
                return "store/review";
            }
        }
        else {
            rttr.addFlashAttribute("msg", "가게가 등록되지 않았습니다.");
            return "redirect:/store";
        }

    }
    @PostMapping("/review/create")
    public String createReview(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long customerId = (Long) session.getAttribute("customerId");
        Long storeId = (Long) session.getAttribute("storeId");

        String reviewContent = request.getParameter("review_content");
        Double reviewStarpoint = Double.parseDouble(request.getParameter("review_starpoint"));

        reviewService.createReview(storeId, customerId, reviewContent, reviewStarpoint);

        return "redirect:/store/review" ;
    }

    @PostMapping("/review/{id}/update")
    public String update(@PathVariable Long id, @RequestParam String content, @RequestParam Double starpoint) {
        log.info("Update" + id.toString() +":" + content + "," + starpoint.toString());
        reviewService.updateReview(id, content, starpoint);
        return "redirect:/store/review";
    }
    @PostMapping("/review/{id}/delete")
    public String delete(@PathVariable Long id) {
        log.info("Deletion request received!");
        reviewService.deleteReview(id);
        return "redirect:/store/review";
    }

    @PostMapping("/store/review/{id}/comment_update")
    public String updateComment(@PathVariable Long id, @RequestParam String comment) {
        reviewService.updateComment(id, comment);
        return "redirect:/store/review";
    }
    @PostMapping("/store/review/{id}/comment_delete")
    public String deleteComment(@PathVariable Long id) {
        reviewService.deleteComment(id);
        return "redirect:/store/review";
    }

    @PostMapping("/store/review/{id}/report")
    public String requestReportReview(@PathVariable Long id, @RequestParam String content, RedirectAttributes rttr) {
        AdminDTO adminDTO = new AdminDTO(id, content);
        AdminEntity existedAdmin = adminRepository.findAdminById(id);

        if(existedAdmin == null) {
            AdminEntity admin = adminService.createAdmin(adminDTO);
            adminRepository.saveNewReview(admin);
            rttr.addFlashAttribute("success_msg", "삭제 요청되었습니다.");
        } else {
            log.info("Admin with id " + id + " not found.");
            rttr.addFlashAttribute("fail_msg", "이미 삭제 요청한 리뷰입니다.");
        }

        return "redirect:/store/review";
    }



}
