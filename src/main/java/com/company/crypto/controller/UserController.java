package com.company.crypto.controller;

import com.company.crypto.dto.UserDto;
import com.company.crypto.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public String addUser(@RequestParam String username) {
        userService.addUser(username);
        return "redirect:/profile";
    }

    @GetMapping
    public String login() {
        return "login";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        UserDto user = userService.getUserProfile();
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/add")
    public String addUsdt(@RequestParam Double usdt) {
        userService.addUsdt(usdt);
        return "redirect:/profile";
    }
}
