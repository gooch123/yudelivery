package seproject.yudelivery.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import seproject.yudelivery.dto.UserRole;
import seproject.yudelivery.entity.AdminEntity;
import seproject.yudelivery.entity.UserEntity;
import seproject.yudelivery.service.AdminService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping// admin main page
    public String adminMain(HttpServletRequest request){
        UserEntity user = (UserEntity) request.getSession().getAttribute("user");
        if(user == null || user.getRole() != UserRole.ADMIN) { // 로그인 안했을때
            return "redirect:/login";
        }
        return "admin/main";
    }

    /**
     * 악성 리뷰 확인
     */
    @GetMapping("/review")
    public String getReview(Model model) {
        List<AdminEntity> reviews = new ArrayList<>();
        reviews.addAll(adminService.findAllReview());
        reviews.addAll(adminService.getAllBadReview());

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

    /**
     * 고객 관리
     */
    @GetMapping("/customer")
    public String getAllCustomer(Model model) {
        List<UserEntity> customers = adminService.getAllUsers().stream()
                .filter(e -> e.getRole() == UserRole.CUSTOMER)
                .filter(e -> e.isBanned() == false)
                .collect(Collectors.toList());

        model.addAttribute("customers", customers);
        return "admin/customer";
    }

    @PostMapping("/customer")
    public String banCustomer(
            @RequestParam("id") Long id,
            @RequestParam("banned_reason") String banned_reason) {
        adminService.banUserById(id, banned_reason);

        return "redirect:/admin/customer";
    }

    /**
     * 가게 관리
     */
    @GetMapping("/store")
    public String getAllStore(Model model) {
        List<UserEntity> stores = adminService.getAllUsers().stream()
                .filter(e -> e.getRole() == UserRole.STORE)
                .filter(e -> e.isBanned() == false)
                .collect(Collectors.toList());

        model.addAttribute("stores", stores);
        return "admin/store";
    }

    @PostMapping("/store")
    public String banStore(
            @RequestParam("id") Long id,
            @RequestParam("banned_reason") String banned_reason) {
        adminService.banUserById(id, banned_reason);

        return "redirect:/admin/store";
    }

    /**
     * 라이더 관리
     */
    @GetMapping("/rider")
    public String getAllRider(Model model) {
        List<UserEntity> riders = adminService.getAllUsers().stream()
                .filter(e -> e.getRole() == UserRole.RIDER)
                .filter(e -> e.isBanned() == false)
                .collect(Collectors.toList());

        model.addAttribute("riders", riders);
        return "admin/rider";
    }

    @PostMapping("/rider")
    public String banRider(
            @RequestParam("id") Long id,
            @RequestParam("banned_reason") String banned_reason) {
        adminService.banUserById(id, banned_reason);

        return "redirect:/admin/rider";
    }
}
