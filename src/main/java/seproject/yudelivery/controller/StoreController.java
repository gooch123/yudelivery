package seproject.yudelivery.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import seproject.yudelivery.dto.OrderFoodDTO;
import seproject.yudelivery.dto.OrderStatus;
import seproject.yudelivery.dto.StoreDTO;
import seproject.yudelivery.entity.*;
import seproject.yudelivery.repository.FoodRepository;
import seproject.yudelivery.repository.OrderRepository;
import seproject.yudelivery.repository.UserRepository;
import seproject.yudelivery.service.OrderService;
import seproject.yudelivery.service.StoreService;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private StoreService storeService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;

    @GetMapping// store main page
    public String storeMain() {
        return "store/main";
    }

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
        return "redirect:/store/my";
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
        log.info(storeDTO.toString());
        StoreEntity store = storeService.updateStore(storeDTO);
        rttr.addFlashAttribute("msg", "가게가 수정되었습니다.");
        log.info(store.toString());
        model.addAttribute("store", store);
        return "redirect:/store/my";
    }

    @GetMapping("/edit")
    public String editStore(HttpServletRequest request, Model model) { // 점주 스토어 수정
        StoreEntity store = findUserStore(request);
        model.addAttribute("store", store);
        return "store/editStore";
    }

    public StoreEntity findUserStore(HttpServletRequest request) {
        UserEntity user = (UserEntity) request.getSession().getAttribute("user");
        if(user == null) { // 로그인 안했을때 (임시)
            user = userRepository.findByUserId("admin").orElse(null);
        }
        StoreEntity store = (StoreEntity) request.getSession().getAttribute("store");
        log.info("store : " + store);

        // 세션에 스토어 정보가 없으면 데이터베이스에서 가져와 세션에 저장
        // 세션에 대한 정보에 따른 처리 필요
        if (store == null) {
            store = storeService.getMyStore(user.getId());
            //request.getSession().setAttribute("store", store);
        }
        return store;
    }

    @GetMapping("/my") // my store page
    public String getMyStore(HttpServletRequest request, Model model,RedirectAttributes rttr) {
        StoreEntity store = findUserStore(request);
        if(store == null) {
            rttr.addFlashAttribute("msg", "가게가 존재하지 않습니다");
            return "redirect:/store";
        }
        List<FoodEntity> foods = foodRepository.findAllByStoreId(store.getId());
        model.addAttribute("store", store);
        model.addAttribute("food", foods);
        return "store/info";
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

    @GetMapping("/order")
    public String getOrders(Model model, HttpServletRequest request, RedirectAttributes rttr) {
        StoreEntity store = findUserStore(request);
        List<OrderEntity> orders = orderRepository.findAllByStore_Id(store.getId());
        if(orders.size() == 0) {
            rttr.addFlashAttribute("msg", "주문이 없습니다.");
            return "redirect:/store";
        }
        log.info("orders : " + orders.get(0).toString());
        log.info("order status : " + orders.get(0).getStatus());
        model.addAttribute("orders", orders);
        return "Order/orderStore"; // JSP 또는 Thymeleaf 페이지
    }

    @ResponseBody
    @GetMapping("/order/{order_id}")
    public List<OrderFoodDTO> getOrderFoods(@PathVariable Long order_id) {
        log.info("getOrderFoods");
        return orderService.getOrderFoods(order_id);
    }

    @ResponseBody
    @PostMapping("/order/{order_id}/accept")
    public void acceptOrder(@PathVariable Long order_id, HttpServletRequest request) {
        StoreEntity store = findUserStore(request);
        OrderEntity order = orderRepository.findById(order_id).orElse(null);
        order.changeStatus(OrderStatus.COOKING);
        orderRepository.save(order);
        store.setSales(store.getSales() + order.getTotalPrice());
        storeService.updateStore(store);
    }
    @ResponseBody
    @PostMapping("/order/{order_id}/deny")
    public void denyOrder(@PathVariable Long order_id) {
        OrderEntity order = orderRepository.findById(order_id).orElse(null);
        order.changeStatus(OrderStatus.CANCEL);
        orderRepository.save(order);
    }

    @ResponseBody
    @PostMapping("/order/{order_id}/pickup")
    public void pickupOrder(@PathVariable Long order_id) {
        OrderEntity order = orderRepository.findById(order_id).orElse(null);
        order.changeStatus(OrderStatus.DELIVERING);
        orderRepository.save(order);
    }
}