package com.company.crypto.controller.rest;

import com.company.crypto.dto.AddUsdtDto;
import com.company.crypto.dto.UserRequest;
import com.company.crypto.dto.UserDto;
import com.company.crypto.entity.User;
import com.company.crypto.jwt.JwtProvider;
import com.company.crypto.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
public class UserRestController {

    private final UserService userService;

    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequest loginRequest) {
        userService.login(loginRequest.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtProvider.generateToken(loginRequest.getUsername()));
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public UserDto profile(@AuthenticationPrincipal User user) {
        return userService.getUserProfile(user.getUsername());
    }

    @GetMapping("/rating")
    public List<UserDto> ratingList() {
        return userService.getRatingList();
    }

    @PostMapping("/add/usdt")
    public UserDto addUsdt(@RequestBody AddUsdtDto usdt, @AuthenticationPrincipal User user) {
        userService.addUsdt(usdt.getUsdt(), user.getUsername());
        return userService.getUserProfile(user.getUsername());
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
