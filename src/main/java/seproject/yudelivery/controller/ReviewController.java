package seproject.yudelivery.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import seproject.yudelivery.dto.ReviewDTO;
import seproject.yudelivery.entity.ReviewEntity;
import seproject.yudelivery.repository.ReviewRepository;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/store/{storeId}/review")
public class ReviewController {
    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping
    public String reviewMain(@PathVariable Long storeId, Model model){
        List<ReviewEntity> reviewList = reviewRepository.findAllByStoreId(storeId);
        model.addAttribute("reviewList", reviewList);
        return "review/list";
    }

    @PostMapping("/create")
    public String createReview(@PathVariable Long storeId, @RequestParam Long customerId, ReviewDTO reviewDTO){
        //reviewDTO.setCustomer_id(customerId); // customerId 설정

        ReviewEntity reviewEntity = reviewDTO.toEntity();
        ReviewEntity saved = reviewRepository.save(reviewEntity);
        log.info(saved.toString());
        return "redirect:/store/" + storeId + "/review/" + saved.getId();
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long storeId, @PathVariable Long id, Model model) {
        log.info("Store ID: " + storeId + ", Review ID: " + id);
        ReviewEntity reviewEntity = reviewRepository.findById(id).orElse(null);
        model.addAttribute("review", reviewEntity);
        return "review/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long storeId, @PathVariable Long id, Model model) {
        ReviewEntity reviewEntity = reviewRepository.findById(id).orElse(null);
        model.addAttribute("food", reviewEntity);
        return "review/edit";
    }

    @PostMapping("/update")
    public String update(@PathVariable Long storeId, ReviewDTO reviewDTO) {
        log.info(reviewDTO.toString());
        ReviewEntity reviewEntity = reviewDTO.toEntity();
        log.info(reviewEntity.toString());
        ReviewEntity target = reviewRepository.findById(reviewEntity.getId()).orElse(null);
        if (target != null) {
            reviewRepository.save(reviewEntity);
        }
        return "redirect:/store/" + storeId + "/review/" + reviewEntity.getId();
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long storeId, @PathVariable Long id) {
        log.info("Deletion request received!");
        ReviewEntity target = reviewRepository.findById(id).orElse(null);
        log.info(target.toString());
        if (target != null) {
            reviewRepository.delete(target);
        }
        return "redirect:/store/" + storeId + "/review";
    }
}
