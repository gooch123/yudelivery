package seproject.yudelivery.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import seproject.yudelivery.dto.StoreDTO;
import seproject.yudelivery.entity.StoreEntity;
import seproject.yudelivery.entity.UserEntity;
import seproject.yudelivery.service.StoreService;

@Controller
@Slf4j
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private StoreService storeService;
    @RequestMapping("/create")
    public String createStore(StoreDTO storeDTO) {
        StoreEntity store = storeService.createStore(storeDTO);
        log.info(store.toString());
        return "redirect:/store/main";
    }

    @GetMapping("/{id}/delete")
    public String deleteStore(@PathVariable Long id) {
        return "";
    }

    @GetMapping("/update")
    public String updateStore() {
        return "";
    }

    @GetMapping("/{id}") // store detail page
    public String getStore(@PathVariable Long id) {
        return "";
    }

    @GetMapping// store main page
    public String storeMain() {
        return "store/main";
    }
    @GetMapping("/new") // store 생성 페이지(user 의 store 없을 때만 가능)
    public String newStore(HttpServletRequest request, RedirectAttributes rttr) {
        UserEntity user = (UserEntity) request.getSession().getAttribute("user");
        if(storeService.getMyStore(user.getId()) != null) {
            rttr.addFlashAttribute("msg", "이미 가게가 존재합니다.");
            return "redirect:/store/main";
        }
        return "store/new";
    }
}
