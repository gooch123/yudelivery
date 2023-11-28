package seproject.yudelivery.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import seproject.yudelivery.dto.BasketDTO;
import seproject.yudelivery.dto.UserRole;
import seproject.yudelivery.entity.UserEntity;
import seproject.yudelivery.service.BasketService;
import seproject.yudelivery.service.OrderService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final BasketService basketService;

    @PostMapping("/order")
    public String order(@SessionAttribute(name = "user",required = false)UserEntity user){
//        if(user == null || user.getRole() != UserRole.CUSTOMER){
//            return null;
//        }
//        Long userId = user.getId();
        Long userId = 1L;

        List<BasketDTO> basketList = basketService.getBasketList(userId);
        if(basketList.isEmpty()){
            //리다이렉트 팝업창
            return "redirect:/basket";
        }

        orderService.createOrder(userId);
        return "redirect:/basket";
    }

}
