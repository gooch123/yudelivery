package seproject.yudelivery.controller;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import seproject.yudelivery.dto.WishListDTO;
import seproject.yudelivery.entity.UserEntity;
import seproject.yudelivery.service.WishListService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/info/wishList")
public class WishListController {

    private final WishListService wishListService;

    @GetMapping
    public String home(Model model, @SessionAttribute(name = "user",required = false) UserEntity user){
//        if(user == null || user.getRole() != UserRole.CUSTOMER){
//            return null;
//        }
//        Long userId = user.getId();
        Long userId = 1L;
        List<WishListDTO> wishList = wishListService.getWishList(userId);
        model.addAttribute("wishList",wishList);

        return "customer/info/wishList";
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable("id") Long wishListId){
        wishListService.deleteWishList(wishListId);
        return "redirect:/info/wishList";
    }


}
