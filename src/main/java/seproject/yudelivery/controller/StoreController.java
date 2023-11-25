package seproject.yudelivery.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
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
    public String createStore(@ModelAttribute("storeDTO") StoreDTO storeDTO, RedirectAttributes rttr) {
        StoreEntity store = storeService.createStore(storeDTO);
        rttr.addFlashAttribute("msg", "가게가 생성되었습니다.");
        log.info(store.toString());
        return "redirect:/store/"+store.getId();
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
    public String getStore(@PathVariable Long id, Model model) {
        StoreEntity store = storeService.getStoreById(id);
        model.addAttribute("store", store);
        return "store/show";
    }

    @GetMapping("/my") // my store page
    public String getMyStore(HttpServletRequest request, Model model) {
        UserEntity user = (UserEntity) request.getSession().getAttribute("user");
        if(user == null) { // 로그인 안했을때 (임시)
            user = new UserEntity();
            user.setId(1L);
        }
        StoreEntity store = storeService.getMyStore(user.getId());
        model.addAttribute("store", store);
        return "store/show";
    }

    @GetMapping// store main page
    public String storeMain() {
        return "store/main";
    }
    @GetMapping("/new") // store 생성 페이지(user 의 store 없을 때만 가능)
    public String newStore(Model model, HttpServletRequest request, RedirectAttributes rttr) {
        UserEntity user = (UserEntity) request.getSession().getAttribute("user");
        if(user == null) { // 로그인 안했을때 (임시)
            user = new UserEntity();
            user.setId(1L);
            if (storeService.getMyStore(user.getId()) != null) { // 이미 가게가 있을때
                rttr.addFlashAttribute("msg", "이미 가게가 존재합니다.");
                return "redirect:/store";
            }
        }
        model.addAttribute("storeDTO", new StoreDTO());
        return "store/new";
    }
}
