package seproject.yudelivery.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import seproject.yudelivery.dto.BasketDTO;
import seproject.yudelivery.dto.UserRole;
import seproject.yudelivery.entity.StoreEntity;
import seproject.yudelivery.entity.UserEntity;
import seproject.yudelivery.service.BasketService;
import seproject.yudelivery.service.StoreService;

import java.util.List;

@Controller
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @GetMapping("/main")
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


        return "basket/main";
    }

    @PostMapping("/{id}/cancel")
    public String cancel(@PathVariable(name = "id") Long basketFoodId){
        basketService.cancelFood(basketFoodId);
        return "redirect:/basket/main";
    }

    @PostMapping("/{id}/add")
    public String addFoodQuantity(@PathVariable(name = "id") Long basketFoodId){
        basketService.updateBasketFoodQuantity(basketFoodId,1);
        return "redirect:/basket/main";
    }

    @PostMapping("/{id}/sub")
    public String subFoodQuantity(@PathVariable(name = "id") Long basketFoodId){
        basketService.updateBasketFoodQuantity(basketFoodId,-1);
        return "redirect:/basket/main";
    }

    //장바구니 추가 기능 구현


}
