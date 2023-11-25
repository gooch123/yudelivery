package seproject.yudelivery.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import seproject.yudelivery.service.WishListService;

@Controller
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;

    @GetMapping("/info/wishList")
    public String home(Model model){

        return "info/wishList";
    }

}
