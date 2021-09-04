package com.company.crypto.controller.rest;

import com.company.crypto.dto.PriceDto;
import com.company.crypto.dto.UserDto;
import com.company.crypto.service.PriceService;
import com.company.crypto.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/price")
public class PriceRestController {
    private final PriceService priceService;

    @GetMapping("/list")
    public String getAllPrices(Model model) {
        List<PriceDto> prices = priceService.getAll();
        model.addAttribute("prices", prices);
        return "pricesList";
    }
}