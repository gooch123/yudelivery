package seproject.yudelivery.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import seproject.yudelivery.dto.CustomerReviewDTO;
import seproject.yudelivery.dto.OrderFoodDTO;
import seproject.yudelivery.dto.OrderViewDTO;
import seproject.yudelivery.entity.UserEntity;
import seproject.yudelivery.service.OrderService;
import seproject.yudelivery.service.ReviewService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/info")
public class CustomerInfoController {

    private final OrderService orderService;
    private final ReviewService reviewService;

    @GetMapping("/orderList")
    public String orderList(@SessionAttribute(name = "user",required = false) UserEntity user, Model model){
//        if(user == null || user.getRole() != UserRole.CUSTOMER){
//            return null;
//        }
//        Long userId = user.getId();

        Long userId = 1L;

        List<OrderViewDTO> orderList = orderService.getOrderViewList(userId);
        model.addAttribute("orderList",orderList);
        return "info/orderList";
    }

    @GetMapping("/orderDetail/{id}")
    public String orderDetail(
            Model model,
            @SessionAttribute(name = "user",required = false) UserEntity user,
            @PathVariable("id") Long orderId){
//        if(user == null || user.getRole() != UserRole.CUSTOMER){
//            return null;
//        }
        List<OrderFoodDTO> orderFoods = orderService.getOrderFoods(orderId);
        model.addAttribute("orderFoods",orderFoods);
        return "info/orderDetail";
    }

    @GetMapping("/review")
    public String reviewList(
            @SessionAttribute(name = "user",required = false) UserEntity user,
            Model model){
//        if(user == null || user.getRole() != UserRole.CUSTOMER){
//            return null;
//        }
//        Long userId = user.getId();
        Long userId = 1L;
        List<CustomerReviewDTO> reviewList = reviewService.getReviewListByCustomer(userId);
        model.addAttribute("reviewList",reviewList);
        return "info/reviewList";
    }

    @PostMapping("/review/{id}/delete")
    public String deleteReview(@PathVariable("id") Long reviewId){
        reviewService.deleteReview(reviewId);
        return "redirect:/info/review";
    }

}
