package com.example.currency_exchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class CurrencyExchangeController {

    private final ExchangeRateService exchangeRateService;

    @Autowired
    public CurrencyExchangeController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("currencies", exchangeRateService.getCurrencyNames());
        model.addAttribute("conversionRequest", new CurrencyConversionRequest());
        return "index";
    }

    @PostMapping("/convert")
    public String convertCurrency(@ModelAttribute("conversionRequest") CurrencyConversionRequest request,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        try {
            ConversionResult result = exchangeRateService.convertCurrency(request);
            model.addAttribute("result", result);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error converting currency: " + e.getMessage());
        }

        model.addAttribute("currencies", exchangeRateService.getCurrencyNames());
        model.addAttribute("conversionRequest", request);
        return "index";
    }
}