package seproject.yudelivery.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import seproject.yudelivery.dto.BasketDTO;
import seproject.yudelivery.entity.UserEntity;
import seproject.yudelivery.service.BasketService;

import java.util.List;

@Controller
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @GetMapping("/main")
    public String basketHome(Model model, HttpSession httpSession){
        UserEntity user = (UserEntity) httpSession.getAttribute("user");
        if(user == null){
            return "home"; //로그인이 안되어있으면 돌려보냄
            // 유저 타입이 맞지 않아도 home 으로 돌려보냄
        }
        long userId = user.getId();
        List<BasketDTO> basketDTOList = basketService.getBasket(userId);
        model.addAttribute("list",basketDTOList);

        return "basket/main";
    }

}
