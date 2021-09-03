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

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class PriceController {
    private final PriceService priceService;

    @GetMapping("/prices")
    public String profile(Model model) {
        List<PriceDto> priceDtoList = priceService.getAll();
        model.addAttribute("prices", priceDtoList);
        return "pricesList";
    }
}
