package com.company.crypto.controller;

import com.company.crypto.dto.AssetDto;
import com.company.crypto.dto.UserDto;
import com.company.crypto.service.AuthorizationService;
import com.company.crypto.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {
    private final AuthorizationService authorizationService;
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
        String username = authorizationService.getProfileOfCurrent().getUsername();
        List<AssetDto> assetDtoList = userService.showUserPortfolio(username);
        model.addAttribute("username", username);
        model.addAttribute("userMoney", userService.userMoneyShower(username));
        model.addAttribute("portfolio", assetDtoList);
        model.addAttribute("allAssets", userService.showAllOfAssets(username));
        model.addAttribute("portfolioSummary", userService.userPortfolioSum(username));
        return "profile";
    }

    @PostMapping("/profile")
    public String addUsdt(@RequestParam Double usdt) {
        userService.addUsdt(usdt);
        return "redirect:/profile";
    }
}
