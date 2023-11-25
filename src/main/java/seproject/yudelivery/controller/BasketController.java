package seproject.yudelivery.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import seproject.yudelivery.dto.BasketDTO;
import seproject.yudelivery.service.BasketService;
import seproject.yudelivery.service.StoreService;

import java.util.List;

@Controller
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;
    private final StoreService storeService;

    @GetMapping("/main")
    public String basketHome(Model model, HttpSession httpSession){
//        UserEntity user = (UserEntity) httpSession.getAttribute("user");
//        if(user == null){
//            return "home"; //로그인이 안되어있으면 돌려보냄
//            // 유저 타입이 맞지 않아도 home 으로 돌려보냄
//        }
//        long userId = user.getId();
        Long userId = 1L;
        List<BasketDTO> basketDTOList = basketService.getBasketList(userId);
        int totalPrice = basketService.getTotalPrice(userId);

        model.addAttribute("totalPrice",totalPrice);
        model.addAttribute("list",basketDTOList);
        model.addAttribute("store",null);

        return "basket/main";
    }

    @PostMapping("/{id}/cancel")
    public String cancel(@PathVariable(name = "id") Long basketFoodId, HttpSession httpSession){
        //        UserEntity user = (UserEntity) httpSession.getAttribute("user");
//        if(user == null){
//            return "home"; //로그인이 안되어있으면 돌려보냄
//            // 유저 타입이 맞지 않아도 home 으로 돌려보냄
//        }
        basketService.cancelFood(basketFoodId);
        return "redirect:/basket/main";
    }


}
