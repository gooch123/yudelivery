package seproject.yudelivery.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import seproject.yudelivery.dto.FoodDTO;
import seproject.yudelivery.dto.UserRole;
import seproject.yudelivery.entity.FoodEntity;
import seproject.yudelivery.entity.OrderFoodEntity;
import seproject.yudelivery.entity.StoreEntity;
import seproject.yudelivery.entity.UserEntity;
import seproject.yudelivery.repository.FoodRepository;
import seproject.yudelivery.repository.OrderFoodRepository;
import seproject.yudelivery.service.StoreService;


@Slf4j
@Controller
@RequestMapping("/food")
public class FoodController{

    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private StoreController storeController;
    @Autowired
    private OrderFoodRepository orderFoodRepository;
    @Autowired
    private StoreService storeService;

    @PostMapping("/create")
    public String create(@ModelAttribute("foodDTO") FoodDTO foodDTO, @SessionAttribute(name = "user", required = false) UserEntity user) {
        if(user == null || user.getRole() != UserRole.STORE){
            return "redirect:/login";
        }
        log.info(foodDTO.toString());
        FoodEntity food = foodDTO.toEntity();
        StoreEntity store = storeService.getStoreById(user.getId());
        food.setStore(store);
        FoodEntity saved = foodRepository.save(food);
        log.info(saved.toString());
        return "redirect:/store/my";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        FoodEntity foodEntity = foodRepository.findById(id).orElse(null);
        model.addAttribute("food", foodEntity);
        return "food/edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("foodDTO") FoodDTO foodDTO) {
        log.info(foodDTO.toString());
        FoodEntity target = foodRepository.findById(foodDTO.getId()).orElse(null);
        foodDTO.setStore(target.getStore());
        FoodEntity foodEntity = foodDTO.toEntity();
        log.info(foodEntity.toString());
        if (target != null) {
            foodRepository.save(foodEntity);
        }
        return "redirect:/store/my";
    }

    @GetMapping("/new")
    public String newFood(Model model){
        model.addAttribute("foodDTO", new FoodDTO());
        return "food/new";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        OrderFoodEntity orderFood = orderFoodRepository.findByFood_Id(id);
        if(orderFood == null){
            foodRepository.findById(id).ifPresent(target -> foodRepository.delete(target));
            return "redirect:/store/my";
        }
        rttr.addFlashAttribute("msg", "You cannot delete the food because it is in your order.");
        return "redirect:/store";
    }
}

