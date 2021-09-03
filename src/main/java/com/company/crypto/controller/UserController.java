package com.company.crypto.controller;

import com.company.crypto.dto.UserDto;
import com.company.crypto.service.AuthorizationService;
import com.company.crypto.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class UserController {
    private final AuthorizationService authorizationService;
    private final UserService userService;

    @PostMapping("/")
    public String addUser(@Valid UserDto user) {
        userService.addUser(user);
        return "redirect:/profile";
    }

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @GetMapping(value = {"/profile"})
    public String profile(Model model) {
        String username = authorizationService.getProfileOfCurrent().getUsername();
        model.addAttribute("username", username);
        return "profile";
    }
}
