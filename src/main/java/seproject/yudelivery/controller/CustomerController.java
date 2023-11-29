package seproject.yudelivery.controller;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import seproject.yudelivery.dto.CustomerReviewDTO;
import seproject.yudelivery.dto.OrderFoodDTO;
import seproject.yudelivery.dto.OrderViewDTO;
import seproject.yudelivery.dto.UpdateCustomerForm;
import seproject.yudelivery.entity.CustomerEntity;
import seproject.yudelivery.entity.StoreEntity;
import seproject.yudelivery.entity.UserEntity;
import seproject.yudelivery.service.OrderService;
import seproject.yudelivery.service.ReviewService;
import seproject.yudelivery.service.StoreService;
import seproject.yudelivery.service.UserService;

import java.util.List;

@Controller
@Transactional // 테스트용 어노테이션
@RequiredArgsConstructor
public class CustomerController {

    private final OrderService orderService;
    private final StoreService storeService;
    private final ReviewService reviewService;
    private final UserService userService;
    @Autowired //테스트용
    private EntityManager em;

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

        List<OrderViewDTO> orderList = orderService.getCompleteCancelOrderViewList(userId);
        model.addAttribute("orderList",orderList);
        return "customer/info/orderList";
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
        return "customer/info/orderDetail";
    }

    /**
     * 주문 현황 확인
     */
    @GetMapping("/info/orderStatus")
    public String orderStatus(
            Model model,
            @SessionAttribute(name = "user",required = false)UserEntity user
            ){

        Long customerId = 1L;
        List<OrderViewDTO> orderList = orderService.getCookingDeliveringWaitOrderViewList(customerId);
        model.addAttribute("orderList",orderList);
        return "customer/info/orderStatus";
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
        return "customer/info/reviewList";
    }

    /**
     * 특정 리뷰 삭제
     */
    @PostMapping("/info/review/{id}/delete")
    public String deleteReview(@PathVariable("id") Long reviewId){
        reviewService.deleteReview(reviewId);
        return "redirect:/info/review";
    }

    @GetMapping("/store/{storeId}")
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

    @GetMapping("/info/update")
    public String editInfo(@SessionAttribute(name = "user",required = false) UserEntity user, Model model){
//        if(user == null || user.getRole() != UserRole.CUSTOMER){
//            return null;
//        }
//        Long userId = user.getId();

        //테스트용이므로 나중에 지울 것
        CustomerEntity customer = new CustomerEntity();
        customer.setCustomer_name("임시");
        customer.setUserId("goo");
        customer.setPassword("123");
        customer.setPhone("22222");
        customer.setEmail("gooooo");
        customer.setUsername("sssss");
        customer.setNickname("qwerq");
        customer.setCustomer_address("대구");
        customer.setCustomer_birthdate(null);
        em.persist(customer);

        UpdateCustomerForm form = userService.updateViewForm(customer.getId());
        model.addAttribute("form",form);

        return "customer/info/updateInfoForm";
    }

}
