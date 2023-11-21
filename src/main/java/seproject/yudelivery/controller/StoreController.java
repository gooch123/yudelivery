package seproject.yudelivery.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import seproject.yudelivery.dto.StoreDTO;
import seproject.yudelivery.entity.StoreEntity;
import seproject.yudelivery.service.StoreService;

@Controller
@Slf4j
public class StoreController {
    @Autowired
    private StoreService storeService;
    @RequestMapping("/store/create")
    public String createStore(StoreDTO storeDTO) {
        StoreEntity store = storeService.createStore(storeDTO);
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
    public String storeMain() {
        return "store/main";
    }
    @GetMapping("/store/new")
    public String newStore() {

        return "store/new";
    }
}
