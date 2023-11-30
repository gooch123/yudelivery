package seproject.yudelivery.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import seproject.yudelivery.dto.BasketDTO;
import seproject.yudelivery.dto.MessageDTO;
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
    public String order(@SessionAttribute(name = "user",required = false)UserEntity user,Model model){
//        if(user == null || user.getRole() != UserRole.CUSTOMER){
//            return null;
//        }
//        Long userId = user.getId();
        Long userId = 1L;

        List<BasketDTO> basketList = basketService.getBasketList(userId);
        if(basketList.isEmpty()){
            //리다이렉트 팝업창
            MessageDTO messageDTO = new MessageDTO("장바구니가 비었습니다", "/basket", RequestMethod.GET, null);
            return showMessageAndRedirect(messageDTO,model);
        }
        orderService.createOrder(userId);
        MessageDTO messageDTO = new MessageDTO("주문을 완료했습니다", "/info/orderStatus", RequestMethod.GET, null);
        return showMessageAndRedirect(messageDTO,model);
    }

    private String showMessageAndRedirect(final MessageDTO params, Model model){
        model.addAttribute("params",params);
        return "common/redirectMessage";
    }

}