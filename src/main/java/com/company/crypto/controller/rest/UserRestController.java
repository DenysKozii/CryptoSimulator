package com.company.crypto.controller.rest;

import com.company.crypto.dto.AssetDto;
import com.company.crypto.dto.LoginRequest;
import com.company.crypto.dto.UserDto;
import com.company.crypto.dto.UserProfileDto;
import com.company.crypto.service.AuthorizationService;
import com.company.crypto.service.ProfilePageService;
import com.company.crypto.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
public class UserRestController {
    private final AuthorizationService authorizationService;
    private final UserService userService;
    private final ProfilePageService profilePageService;

    @PostMapping("/registration")
    public boolean registration(@RequestBody UserDto user) {
        if (!user.getPassword().equals(user.getConfirmPassword()))
            return false;
        return userService.addUser(user);
    }

    @PostMapping("/login")
    public UserProfileDto login(@RequestBody @Valid LoginRequest request) {
        return userService.loginUser(request);
    }

    @GetMapping("/profile")
    public UserProfileDto profile() {
        return authorizationService.getProfileOfCurrent();
    }
}
