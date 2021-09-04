package com.company.crypto.controller;

import com.company.crypto.dto.AssetDto;
import com.company.crypto.dto.ProfilePageDto;
import com.company.crypto.dto.UserDto;
import com.company.crypto.service.AuthorizationService;
import com.company.crypto.service.ProfilePageService;
import com.company.crypto.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {
    private final AuthorizationService authorizationService;
    private final UserService userService;
    private final ProfilePageService profilePageService;

    @PostMapping("/")
    public String addUser(@Valid UserDto user) {
        userService.addUser(user);
        return "redirect:/profile";
    }

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        String username = authorizationService.getProfileOfCurrent().getUsername();
        List<AssetDto> assetDtoList= profilePageService.showUserPortfolio(username);
        model.addAttribute("username", username);
        model.addAttribute("userMoney", profilePageService.userMoneyShower(username));
        model.addAttribute("portfolio", assetDtoList);
        model.addAttribute("portfolioSummary", profilePageService.userPortfolioSum(username));
        return "profile";
    }
}
