package com.example.currency_exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ExchangeRateService exchangeRateService;

    @Autowired
    public ApiController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping("/rates")
    public ResponseEntity<ExchangeRateResponse> getLatestRates(@RequestParam(defaultValue = "USD") String base) {
        return ResponseEntity.ok(exchangeRateService.getLatestRates(base));
    }

    @PostMapping("/convert")
    public ResponseEntity<ConversionResult> convertCurrency(@RequestBody CurrencyConversionRequest request) {
        return ResponseEntity.ok(exchangeRateService.convertCurrency(request));
    }

    @GetMapping("/currencies")
    public ResponseEntity<List<String>> getAvailableCurrencies() {
        return ResponseEntity.ok(exchangeRateService.getAvailableCurrencies());
    }

    @GetMapping("/currency-names")
    public ResponseEntity<Map<String, String>> getCurrencyNames() {
        return ResponseEntity.ok(exchangeRateService.getCurrencyNames());
    }
}