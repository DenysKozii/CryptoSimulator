package com.company.crypto.controller.rest;

import com.company.crypto.dto.UserDto;
import com.company.crypto.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
public class UserRestController {
    private final UserService userService;

    @PostMapping
    public UserDto addUser(@RequestParam String username) {
        userService.addUser(username);
        return userService.getUserProfile();
    }

    @GetMapping("/profile")
    public UserDto profile() {
        return userService.getUserProfile();
    }

    @PostMapping("/add")
    public UserDto addUsdt(@RequestParam Double usdt) {
        userService.addUsdt(usdt);
        return userService.getUserProfile();
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
