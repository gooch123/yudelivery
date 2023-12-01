package seproject.yudelivery.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import seproject.yudelivery.dto.BasketDTO;
import seproject.yudelivery.dto.MessageDTO;
import seproject.yudelivery.dto.UserRole;
import seproject.yudelivery.entity.StoreEntity;
import seproject.yudelivery.entity.UserEntity;
import seproject.yudelivery.service.BasketService;
import seproject.yudelivery.service.OrderService;
import seproject.yudelivery.service.StoreService;

import java.util.List;

@Controller
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;
    private final OrderService orderService;

    @GetMapping
    public String basketHome(Model model, @SessionAttribute(name = "user",required = false) UserEntity user){
//        if(user == null || user.getRole() != UserRole.CUSTOMER){
//            return null;
//        }
//        Long userId = user.getId();
        Long userId = 1L;
        List<BasketDTO> basketDTOList = basketService.getBasketList(userId);
        int totalPrice = basketService.getTotalPrice(userId);
        String basketStoreName = basketService.getBasketStoreName(userId);

        model.addAttribute("totalPrice",totalPrice);
        model.addAttribute("list",basketDTOList);
        model.addAttribute("store",basketStoreName);

        return "customer/basket/main";
    }

    @PostMapping("/reorder/{id}")
    public String reorder(@PathVariable("id") Long orderId,Model model){
        try {
            basketService.reorder(orderId);
        } catch (IllegalStateException e) {
            MessageDTO message = new MessageDTO(e.getMessage(), "/basket", RequestMethod.GET, null);
            return showMessageAndRedirect(message,model);
        }
        return "redirect:/basket";
    }

    @PostMapping("/{id}/cancel")
    public String cancel(@PathVariable(name = "id") Long basketFoodId){
        basketService.cancelFood(basketFoodId);
        return "redirect:/basket";
    }

    @PostMapping("/{id}/add")
    public String addFoodQuantity(@PathVariable(name = "id") Long basketFoodId){
        basketService.updateBasketFoodQuantity(basketFoodId,1);
        return "redirect:/basket";
    }

    @PostMapping("/{id}/sub")
    public String subFoodQuantity(@PathVariable(name = "id") Long basketFoodId){
        basketService.updateBasketFoodQuantity(basketFoodId,-1);
        return "redirect:/basket";
    }

    //장바구니 추가 기능 구현
    @PostMapping("/addBasket")
    public String addFoodToBasket(
            @RequestParam(name = "foodId") Long foodId,
            @RequestParam("quantity")int quantity,
            @SessionAttribute(name = "user",required = false)UserEntity user,
            Model model,
            HttpServletRequest request){
//        if(user == null || user.getRole() != UserRole.CUSTOMER){
//            return null;
//        }
        Long userId = 1L;
        String referer = request.getHeader("Referer");

        try {
            basketService.addFoodToBasket(foodId,quantity,userId);
        } catch (IllegalStateException e) {
            MessageDTO messageDTO = new MessageDTO(e.getMessage(), "/basket", RequestMethod.GET, null);
            return showMessageAndRedirect(messageDTO,model);
        }

        return "redirect:" + referer;
    }

    private String showMessageAndRedirect(final MessageDTO params,Model model){
        model.addAttribute("params",params);
        return "common/redirectMessage";
    }

}
