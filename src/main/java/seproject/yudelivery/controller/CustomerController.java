package seproject.yudelivery.controller;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import seproject.yudelivery.dto.CustomerReviewDTO;
import seproject.yudelivery.dto.OrderFoodDTO;
import seproject.yudelivery.dto.OrderViewDTO;
import seproject.yudelivery.entity.StoreEntity;
import seproject.yudelivery.entity.UserEntity;
import seproject.yudelivery.service.OrderService;
import seproject.yudelivery.service.ReviewService;
import seproject.yudelivery.service.StoreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final OrderService orderService;
    private final StoreService storeService;
    private final ReviewService reviewService;

    /**
     * 주문내역(완료) 출력
     */
    @GetMapping("/info/orderList")
    public String orderList(@SessionAttribute(name = "user",required = false) UserEntity user, Model model){
//        if(user == null || user.getRole() != UserRole.CUSTOMER){
//            return null;
//        }
//        Long userId = user.getId();

        Long userId = 1L;

        List<OrderViewDTO> orderList = orderService.getCompleteOrderViewList(userId);
        model.addAttribute("orderList",orderList);
        return "info/orderList";
    }

    /**
     * 주문 세부내역 출력
     */
    @GetMapping("/info/orderDetail/{id}")
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

    @GetMapping("/info/orderStatus")
    public String orderStatus(
            Model model,
            @SessionAttribute(name = "user",required = false)UserEntity user
            ){


        return null;
    }

    /**
     * 자신의 리뷰 목록 출력
     */
    @GetMapping("/info/review")
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

    /**
     * 특정 리뷰 삭제
     */
    @PostMapping("/info/review/{id}/delete")
    public String deleteReview(@PathVariable("id") Long reviewId){
        reviewService.deleteReview(reviewId);
        return "redirect:/info/review";
    }

    @GetMapping("store/{storeId}")
    public String storeDetail(@PathVariable Long storeId, Model model){
        StoreEntity detail = storeService.getStoreDetail(storeId);
        model.addAttribute("store", detail);
        return "store/detail"; //템플릿 만들기
    }

    @GetMapping("/store/search")
    public String searchStores(Model model){
        String keyword = "null";
        List<StoreEntity> searchResult = storeService.searchStores(keyword);
        model.addAttribute("searchResult", searchResult);
        return "store/search"; //템플릿 만들기
    }

}
