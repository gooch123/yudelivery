package seproject.yudelivery.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import seproject.yudelivery.dto.StoreDTO;
import seproject.yudelivery.entity.StoreEntity;

@Controller
@Slf4j
public class StoreController {
    @RequestMapping("/store/create")
    public String createStore(StoreDTO storeDTO) {
        StoreEntity store = storeDTO.toEntity();
        log.info(store.toString());
        return "redirect: /store/main";
    }

    @GetMapping("/store/{id}/delete")
    public String deleteStore(@PathVariable Long id) {
        return "";
    }

    @GetMapping("/store/update")
    public String updateStore() {
        return "";
    }

    @GetMapping("/store/{id}")
    public String getStore() {
        return "";
    }

    @GetMapping("/store")
    public String mainStore() {
        return "store/main";
    }
    @GetMapping("/store/new")
    public String newStore() {
        return "store/new";
    }
}
