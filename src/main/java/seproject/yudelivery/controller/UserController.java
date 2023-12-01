package seproject.yudelivery.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import seproject.yudelivery.dto.JoinRepuest;
import seproject.yudelivery.dto.LoginRequest;
import seproject.yudelivery.dto.MessageDTO;
import seproject.yudelivery.dto.UserRole;
import seproject.yudelivery.entity.UserEntity;
import seproject.yudelivery.service.UserService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/join")
    public String joinForm(@ModelAttribute UserEntity userEntity) {
        return "/user/join";
    }

    @PostMapping("/join")
    public String save(@RequestParam("email") String email,
                       @RequestParam("password") String password,
                       @RequestParam("name") String username,
                       @RequestParam("nickName") String nickname,
                       @RequestParam("phone") String phone,
                       @RequestParam("ID") String userId,
                       @RequestParam("role") String role,
                       Model model){
        UserRole userRole = null;
        switch (role){
            case "customer":
                userRole = UserRole.CUSTOMER;
                break;
            case "rider":
                userRole = UserRole.RIDER;
                break;
            case "store":
                userRole = UserRole.STORE;
                break;
        }
        JoinRepuest joinRepuest = new JoinRepuest(userId, password, nickname, username, email, phone,userRole);
        try {
            userService.join(joinRepuest);
        } catch (IllegalStateException e) {
            MessageDTO messageDTO = new MessageDTO(e.getMessage(), "/join", RequestMethod.GET, null);
            return showMessageAndRedirect(messageDTO,model);
        }

        MessageDTO message = new MessageDTO("회원가입에 성공하였습니다", "/login", RequestMethod.GET, null);
        return showMessageAndRedirect(message,model);
    }

    @GetMapping("/login")
    public String loginForm(){
        return "user/login";
    }

    @PostMapping("/login")
    public String login(
            HttpServletRequest request,
            @RequestParam("userId") String userId,
            @RequestParam("password") String password,
            Model model) {

        LoginRequest loginRequest = new LoginRequest(userId, password);
        UserEntity user;

        try {
            user = userService.login(loginRequest);
        } catch (IllegalStateException e) {
            MessageDTO messageDTO = new MessageDTO(e.getMessage(), "/login", RequestMethod.GET, null);
            return showMessageAndRedirect(messageDTO, model);
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);

        if(user.getRole() == UserRole.CUSTOMER)
            return "redirect:/customer";
        else if(user.getRole() == UserRole.STORE)
            return "redirect:/customer";
        else if(user.getRole() == UserRole.RIDER)
            return "redirect:/login";
        else if(user.getRole() == UserRole.ADMIN)
            return "redirect:/customer";
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        session.invalidate();
        return "redirect:/login";
    }

    private String showMessageAndRedirect(final MessageDTO params, Model model){
        model.addAttribute("params",params);
        return "common/redirectMessage";
    }


}
