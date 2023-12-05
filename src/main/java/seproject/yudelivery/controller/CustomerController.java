package seproject.yudelivery.controller;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import seproject.yudelivery.dto.*;
import seproject.yudelivery.entity.CustomerEntity;
import seproject.yudelivery.entity.FoodEntity;
import seproject.yudelivery.entity.StoreEntity;
import seproject.yudelivery.entity.UserEntity;
import seproject.yudelivery.service.OrderService;
import seproject.yudelivery.service.ReviewService;
import seproject.yudelivery.service.StoreService;
import seproject.yudelivery.service.UserService;
import seproject.yudelivery.service.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final OrderService orderService;
    private final StoreService storeService;
    private final ReviewService reviewService;
    private final UserService userService;


    @GetMapping("/customer")
    public String customerMain(@SessionAttribute(name = "user",required = false)UserEntity user){
        if(user == null || user.getRole() != UserRole.CUSTOMER){
            return "redirect:/login";
        }
        return "customer/main";
    }

    /**
     * 주문내역(완료) 출력
     */
    @GetMapping("/info/orderList")
    public String orderList(@SessionAttribute(name = "user",required = false) UserEntity user, Model model){
        if(user == null || user.getRole() != UserRole.CUSTOMER){
            return "redirect:/login";
        }
        Long userId = user.getId();

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
        if(user == null || user.getRole() != UserRole.CUSTOMER){
            return "redirect:/login";
        }
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
        if(user == null || user.getRole() != UserRole.CUSTOMER){
            return "redirect:/login";
        }

        List<OrderViewDTO> orderList = orderService.getCookingDeliveringWaitOrderViewList(user.getId());
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
        if(user == null || user.getRole() != UserRole.CUSTOMER){
            return "redirect:/login";
        }
        Long userId = user.getId();
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
        return "store/detail";
    }

    @GetMapping("/store/search")
    public String searchStores(@RequestParam(required = false, defaultValue = "") String keyword, Model model){
        List<StoreEntity> searchResult = storeService.searchStores(keyword);
        model.addAttribute("searchResult", searchResult);
        return "store/search"; //템플릿 만들기
    }

    /**
     * 고객 정보 수정
     */
    @GetMapping("/info/update")
    public String editInfoForm(@SessionAttribute(name = "user",required = false) UserEntity user, Model model){
        if(user == null || user.getRole() != UserRole.CUSTOMER){
            return "redirect:/login";

        }
        Long userId = user.getId();

        UpdateCustomerForm form = userService.updateViewForm(userId);
        model.addAttribute("form",form);

        return "customer/info/updateInfoForm";
    }

    @PostMapping("/info/update")
    public String editInfo(
            @SessionAttribute(name = "user",required = false) UserEntity user,
            @Validated @ModelAttribute("form") UpdateCustomerForm form,
            BindingResult bindingResult,
            Model model){
        if(user == null || user.getRole() != UserRole.CUSTOMER){
            return "redirect:/login";
        }
        log.info("object = {}",bindingResult.getObjectName());
        log.info("target = {}",bindingResult.getTarget());

        if(bindingResult.hasErrors()){
            log.info("errors = {}",bindingResult);
            return "customer/info/updateInfoForm";
        }

        userService.updateCustomer(form);
        MessageDTO messageDTO = new MessageDTO("정보 수정을 완료했습니다", "/info/update", RequestMethod.GET, null);

        return showMessageAndRedirect(messageDTO,model);
    }

    /**
     * 리다이렉트 메시지 창
     */
    private String showMessageAndRedirect(final MessageDTO params, Model model){
        model.addAttribute("params",params);
        return "common/redirectMessage";
    }


}
