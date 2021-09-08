//package com.company.crypto.controller;
//
//import com.company.crypto.dto.PriceDto;
//import com.company.crypto.service.PriceService;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@Controller
//@AllArgsConstructor
//@Slf4j
//@RequestMapping("/price")
//public class PriceController {
//    private final PriceService priceService;
//
//    @GetMapping("/list")
//    public String getAllPrices(Model model) {
//        log.info("Displayed price list page");
//        List<PriceDto> prices = priceService.getAll();
//        model.addAttribute("prices", prices);
//        return "pricesList";
//    }
//}
