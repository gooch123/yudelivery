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
import seproject.yudelivery.repository.UserRepository;
import seproject.yudelivery.service.StoreService;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private StoreService storeService;
    @Autowired
    private UserRepository userRepository;
    @RequestMapping("/create")
    public String createStore(@ModelAttribute("storeDTO") StoreDTO storeDTO, RedirectAttributes rttr, HttpServletRequest request) {
        UserEntity user = (UserEntity) request.getSession().getAttribute("user");
        if(user == null) { // 로그인 안했을때 (임시)
            user = userRepository.findByUserId("admin").orElse(null);
        }
        storeDTO.setUser(user);
        StoreEntity store = storeService.createStore(storeDTO);
        rttr.addFlashAttribute("msg", "가게가 생성되었습니다.");
        log.info(store.toString());
        return "redirect:/store/"+store.getId();
    }

    @GetMapping("/delete") // 점주 스토어 삭제
    public String deleteStore(HttpServletRequest request, RedirectAttributes rttr) {
        UserEntity user = (UserEntity) request.getSession().getAttribute("user");
        if(user == null) { // 로그인 안했을때 (임시)
            //return "redirect:/home";
            user = userRepository.findByUserId("admin").orElse(null);
        }
        storeService.deleteMyStore(user.getId());
        rttr.addFlashAttribute("msg", "가게가 삭제되었습니다.");
        return "redirect:/store";
    }

    @PostMapping("/update")
    public String updateStore(@ModelAttribute("storeDTO") StoreDTO storeDTO, RedirectAttributes rttr,Model model, HttpServletRequest request) {
        UserEntity user = (UserEntity) request.getSession().getAttribute("user");
        if(user == null) { // 로그인 안했을때 (임시)
            //return "redirect:/home";
            user = userRepository.findByUserId("admin").orElse(null);
        }
        storeDTO.setUser(user);
        log.info(storeDTO.toString());
        StoreEntity store = storeService.updateStore(storeDTO);
        rttr.addFlashAttribute("msg", "가게가 수정되었습니다.");
        log.info(store.toString());
        model.addAttribute("store", store);
        return "redirect:/store/my";
    }

    @GetMapping("/editStore")
    public String editStore(HttpServletRequest request, Model model) { // 점주 스토어 수정
        StoreEntity store = findUserStore(request);
        model.addAttribute("store", store);
        return "store/editStore";
    }

    private StoreEntity findUserStore(HttpServletRequest request) {
        UserEntity user = (UserEntity) request.getSession().getAttribute("user");
        if(user == null) { // 로그인 안했을때 (임시)
            user = userRepository.findByUserId("admin").orElse(null);
        }
        StoreEntity store = (StoreEntity) request.getSession().getAttribute("store");
        log.info("store : " + store);

        // 세션에 스토어 정보가 없으면 데이터베이스에서 가져와 세션에 저장
        if (store == null) {
            store = storeService.getMyStore(user.getId());
            //request.getSession().setAttribute("store", store);
        }
        return store;
    }

    @GetMapping("/{id}") // store detail page
    public String getStore(@PathVariable Long id, Model model) {
        StoreEntity store = storeService.getStoreById(id);
        model.addAttribute("store", store);
        return "store/show";
    }

    @GetMapping("/my") // my store page
    public String getMyStore(HttpServletRequest request, Model model,RedirectAttributes rttr) {
        StoreEntity store = findUserStore(request);
        if(store == null) {
            rttr.addFlashAttribute("msg", "가게가 존재하지 않습니다");
            return "redirect:/store";
        }
        model.addAttribute("store", store);
        return "store/info";
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
            StoreEntity store = storeService.getMyStore(user.getId());
            if (store != null) { // 이미 가게가 있을때
                rttr.addFlashAttribute("msg", "이미 가게가 존재합니다.");
                return "redirect:/store";
            }
        }
        model.addAttribute("storeDTO", new StoreDTO());
        return "store/new";
    }
    @GetMapping("/sales") // store 매출 페이지
    public String storeSales(Model model, HttpServletRequest request) {
        StoreEntity store = findUserStore(request);
        model.addAttribute("sales", store.getSales());
        log.info("sales : " + store.getSales());
        return "/store/sales";
    }
}
