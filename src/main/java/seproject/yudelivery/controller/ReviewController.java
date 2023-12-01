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
import seproject.yudelivery.entity.AdminEntity;
import seproject.yudelivery.entity.ReviewEntity;
import seproject.yudelivery.repository.AdminRepository;
import seproject.yudelivery.repository.ReviewRepository;
import seproject.yudelivery.service.AdminService;
import seproject.yudelivery.service.ReviewService;

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

    public ReviewController(ReviewService reviewService, AdminService adminService) {
        this.reviewService = reviewService;
        this.adminService = adminService;
    }


    @GetMapping("/store/review")
    public String reviewMain(HttpServletRequest request, Model model, RedirectAttributes rttr){
        //Long storeId = (Long) request.getSession().getAttribute("storeId");
        Long storeId = 1L;
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

    @GetMapping("/review/create/{id}")
    public String createReviewForm(Model model, @PathVariable("id") Long storeId){
        model.addAttribute("id",storeId);
        return "customer/info/addReviewForm";
    }

    @PostMapping("/review/create")
    public String createReview(
            HttpServletRequest request,
            @RequestParam("id") Long storeId,
            @RequestParam("content") String content,
            @RequestParam("starPoint") Double starPoint) {
        HttpSession session = request.getSession();
        Long customerId = (Long) session.getAttribute("customerId");

        reviewService.createReview(storeId, customerId, content, starPoint);

        return "redirect:/info/review" ;
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
