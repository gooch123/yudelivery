package seproject.yudelivery.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import seproject.yudelivery.dto.OrderDTO;
import seproject.yudelivery.service.OrderService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/info")
public class CustomerInfoController {

    private final OrderService orderService;

    @GetMapping("/orderList")
    public String orderList(HttpSession httpSession, Model model){
//        UserEntity user = (UserEntity) httpSession.getAttribute("user");
//        if(user == null){
//            return "home"; //로그인이 안되어있으면 돌려보냄
//            // 유저 타입이 맞지 않아도 home 으로 돌려보냄
//        }
//        Long userId = user.getId();

        Long userId = 1L;

        List<OrderDTO> orderList = orderService.getOrdersByUserId(userId);
        model.addAttribute("orderList",orderList);
        return null;
    }



}
