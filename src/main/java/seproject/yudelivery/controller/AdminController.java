package seproject.yudelivery.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import seproject.yudelivery.entity.AdminEntity;
import seproject.yudelivery.service.AdminService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping // admin main page
    public String adminMain() {
        return "admin/main";
    }

    @GetMapping("/review")
    public String getReview(Model model) {
        List<AdminEntity> reviews = adminService.findAllReview();

        model.addAttribute("reviews", reviews);
        return "admin/review";
    }

    @GetMapping("/review/delete/{id}")
    public String deleteReview(@PathVariable Long id) {
        adminService.deleteReviewById(id);
        return "redirect:/admin/review";
    }

    @GetMapping("/review/ignore/{id}")
    public String ignoreReview(@PathVariable Long id) {
        adminService.ignoreReviewById(id);
        return "redirect:/admin/review";
    }
}
