//package com.company.crypto.controller;
//
//import com.company.crypto.dto.UserDto;
//import com.company.crypto.service.UserService;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//@Slf4j
//@AllArgsConstructor
//public class UserController {
//    private final UserService userService;
//    private final AuthorizationService authorizationService;
//
//    @PostMapping
//    public String addUser(@RequestParam String username) {
//        userService.login(username);
//        return "redirect:/profile";
//    }
//
//    @GetMapping
//    public String login() {
//        log.info("Displayed login page");
//        return "login";
//    }
//
//    @GetMapping("/profile")
//    public String profile(Model model) {
//        log.info("Displayed profile page");
//        String username = authorizationService.getProfileOfCurrent().getUsername();
//        UserDto user = userService.getUserProfile(username);
//        model.addAttribute("user", user);
//        return "profile";
//    }
//
//    @PostMapping("/add")
//    public String addUsdt(@RequestParam Double usdt) {
//        String username = authorizationService.getProfileOfCurrent().getUsername();
//        userService.addUsdt(usdt,username);
//        return "redirect:/profile";
//    }
//}
