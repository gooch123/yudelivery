package seproject.yudelivery.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import seproject.yudelivery.entity.ReviewEntity;
import seproject.yudelivery.repository.ReviewRepository;
import seproject.yudelivery.service.ReviewService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping
public class ReviewController {
    @Autowired
    private ReviewRepository reviewRepository;
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/store/review")
    public String reviewMain(HttpServletRequest request, Model model, RedirectAttributes rttr){
        //Long storeId = (Long) request.getSession().getAttribute("storeId");
        Long storeId = 1L;
        if (storeId != null) {
            List<ReviewEntity> reviewList = reviewRepository.findAllByStoreId(storeId);
            model.addAttribute("reviewList", reviewList);
            return "store/review";
        }
        else {
            rttr.addFlashAttribute("msg", "가게가 존재하지 않습니다");
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

}
