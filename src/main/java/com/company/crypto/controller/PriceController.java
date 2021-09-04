package com.company.crypto.controller;

import com.company.crypto.dto.PriceDto;
import com.company.crypto.dto.UserDto;
import com.company.crypto.service.AuthorizationService;
import com.company.crypto.service.PriceService;
import com.company.crypto.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/price")
public class PriceController {
    private final PriceService priceService;

    @GetMapping("/list")
    public String getAllPrices(Model model) {
        List<PriceDto> prices = priceService.getAll();
        model.addAttribute("prices", prices);
        return "pricesList";
    }
}
