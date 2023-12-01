package seproject.yudelivery.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import seproject.yudelivery.entity.UserEntity;
import seproject.yudelivery.repository.UserRepository;
import seproject.yudelivery.service.UserService;

import java.lang.reflect.Member;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private  final UserRepository userRepository;

    @GetMapping("/join")
    public String joinForm(@ModelAttribute UserEntity userEntity) {
        return "/join";
    }

    @PostMapping("/join")
    public String save(@Valid @ModelAttribute UserEntity userEntity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/join";
        }
        UserService.

    }


}
