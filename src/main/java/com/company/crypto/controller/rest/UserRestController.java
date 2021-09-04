package com.company.crypto.controller.rest;

import com.company.crypto.dto.UserDto;
import com.company.crypto.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
public class UserRestController {
    private final UserService userService;

    @PostMapping
    public String addUser(@Valid UserDto user) {
        userService.addUser(user);
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

    @PostMapping("/profile")
    public String addUsdt(@RequestParam Double usdt) {
        userService.addUsdt(usdt);
        return "redirect:/profile";
    }

//    @PostMapping("/registration")
//    public boolean registration(@RequestBody UserDto user) {
//        if (!user.getPassword().equals(user.getConfirmPassword()))
//            return false;
//        return userService.addUser(user);
//    }
//
//    @PostMapping("/login")
//    public UserProfileDto login(@RequestBody @Valid LoginRequest request) {
//        return userService.loginUser(request);
//    }
//
//    @GetMapping("/profile")
//    public UserProfileDto profile() {
//        return authorizationService.getProfileOfCurrent();
//    }
}
