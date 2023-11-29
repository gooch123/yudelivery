package seproject.yudelivery.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import seproject.yudelivery.dto.FoodDTO;
import seproject.yudelivery.entity.FoodEntity;
import seproject.yudelivery.entity.StoreEntity;
import seproject.yudelivery.entity.UserEntity;
import seproject.yudelivery.repository.FoodRepository;
import seproject.yudelivery.repository.StoreRepository;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/food")
public class FoodController{

    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private StoreController storeController;
    @Autowired
    private StoreRepository storeRepository;

    @PostMapping("/create")
    public String create(@ModelAttribute("foodDTO") FoodDTO foodDTO, HttpServletRequest request ) {
        log.info(foodDTO.toString());
        FoodEntity food = foodDTO.toEntity();
        StoreEntity store = storeController.findUserStore(request);
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
    public String delete(@PathVariable Long id) {
        log.info("Deletion request received!");
        foodRepository.findById(id).ifPresent(target -> foodRepository.delete(target));
        return "redirect:/store/my";
    }
}

