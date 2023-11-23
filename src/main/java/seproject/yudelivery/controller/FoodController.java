package seproject.yudelivery.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import seproject.yudelivery.dto.FoodDTO;
import seproject.yudelivery.entity.FoodEntity;
import seproject.yudelivery.repository.FoodRepository;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/store/{storeId}/food")
public class FoodController {

    @Autowired
    private FoodRepository foodRepository;

    @GetMapping
    public String foodMain(@PathVariable Long storeId, Model model) {
        List<FoodEntity> foodList = foodRepository.findAllByStoreId(storeId);
        model.addAttribute("foodList", foodList);
        return "food/list";
    }

    @PostMapping("/create")
    public String createFood(@PathVariable Long storeId, FoodDTO foodDTO) {
        FoodEntity foodEntity = foodDTO.toEntity();
        FoodEntity saved = foodRepository.save(foodEntity);
        log.info(saved.toString());
        return "redirect:/store/" + storeId + "/foods/" + saved.getId();
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long storeId, @PathVariable Long id, Model model) {
        log.info("Store ID: " + storeId + ", Food ID: " + id);
        FoodEntity foodEntity = foodRepository.findById(id).orElse(null);
        model.addAttribute("food", foodEntity);
        return "foods/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long storeId, @PathVariable Long id, Model model) {
        FoodEntity foodEntity = foodRepository.findById(id).orElse(null);
        model.addAttribute("food", foodEntity);
        return "foods/edit";
    }

    @PostMapping("/update")
    public String update(@PathVariable Long storeId, FoodDTO foodDTO) {
        log.info(foodDTO.toString());
        FoodEntity foodEntity = foodDTO.toEntity();
        log.info(foodEntity.toString());
        FoodEntity target = foodRepository.findById(foodEntity.getId()).orElse(null);
        if (target != null) {
            foodRepository.save(foodEntity);
        }
        return "redirect:/store/" + storeId + "/foods/" + foodEntity.getId();
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long storeId, @PathVariable Long id) {
        log.info("Deletion request received!");
        FoodEntity target = foodRepository.findById(id).orElse(null);
        log.info(target.toString());
        if (target != null) {
            foodRepository.delete(target);
        }
        return "redirect:/store/" + storeId + "/foods";
    }
}

