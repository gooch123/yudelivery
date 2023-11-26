package seproject.yudelivery.controller;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import seproject.yudelivery.dto.WishListDTO;
import seproject.yudelivery.service.WishListService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/info/wishList")
public class WishListController {

    private final WishListService wishListService;

    @GetMapping
    public String home(Model model, HttpSession httpSession){

        Long userId = 1L;
        List<WishListDTO> wishList = wishListService.getWishList(userId);
        model.addAttribute("wishList",wishList);

        return "info/wishList";
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable("id") Long wishListId){
        wishListService.deleteWishList(wishListId);
        return "redirect:/info/wishList";
    }

}
