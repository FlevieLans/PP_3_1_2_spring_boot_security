package application.pp_3_1_2_spring_boot_security.controller;

import application.pp_3_1_2_spring_boot_security.entity.User;
import application.pp_3_1_2_spring_boot_security.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }


    @GetMapping("/user")
    public String getUserInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = (User) userService.loadUserByUsername(currentUsername);
        model.addAttribute("user", currentUser);
        return "user";
    }

}
