package com.example.currency_exchange;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
public class ExchangeRateService {

    private final RestTemplate restTemplate;

    @Value("${exchange.api.url}")
    private String apiUrl;

    @Value("${exchange.api.key}")
    private String apiKey;

    public ExchangeRateService() {
        this.restTemplate = new RestTemplate();
    }

    // Fetches latest exchange rates from API
    public ExchangeRateResponse getLatestRates(String baseCurrency) {
        System.out.println("Fetching exchange rates for base currency: " + baseCurrency);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl + baseCurrency);

        if (apiKey != null && !apiKey.isEmpty() && !apiKey.equals("YOUR_API_KEY")) {
            builder.queryParam("apikey", apiKey);
        }

        String url = builder.toUriString();
        System.out.println("API Request URL: " + url);

        ExchangeRateResponse response = restTemplate.getForObject(url, ExchangeRateResponse.class);

        if (response != null && response.getRates() != null) {
            System.out.println("Received rates: " + response.getRates());
        } else {
            System.out.println("Failed to fetch valid exchange rates.");
        }

        return response;
    }

    // Converts amount from one currency to another
    public ConversionResult convertCurrency(CurrencyConversionRequest request) {
        System.out.println("Converting from: " + request.getFromCurrency() +
                           " to: " + request.getToCurrency() +
                           " amount: " + request.getAmount());

        ExchangeRateResponse response = getLatestRates(request.getFromCurrency());

        if (response != null && response.getRates() != null) {
            Double rate = response.getRates().get(request.getToCurrency());
            if (rate != null) {
                double convertedAmount = request.getAmount() * rate;
                System.out.println("Conversion successful. Rate: " + rate + ", Result: " + convertedAmount);

                return new ConversionResult(
                    request.getFromCurrency(),
                    request.getToCurrency(),
                    request.getAmount(),
                    convertedAmount,
                    rate
                );
            } else {
                System.out.println("No exchange rate found for target currency: " + request.getToCurrency());
            }
        } else {
            System.out.println("Failed to retrieve exchange rates from API.");
        }

        throw new RuntimeException("Failed to retrieve exchange rate for conversion.");
    }

    // Hardcoded list of available currencies
    public List<String> getAvailableCurrencies() {
        return Arrays.asList("USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "CNY", "INR", "RUB");
    }

    // Returns map of currency codes to full names
    public Map<String, String> getCurrencyNames() {
        Map<String, String> currencyNames = new TreeMap<>();
        currencyNames.put("USD", "US Dollar");
        currencyNames.put("EUR", "Euro");
        currencyNames.put("GBP", "British Pound");
        currencyNames.put("JPY", "Japanese Yen");
        currencyNames.put("AUD", "Australian Dollar");
        currencyNames.put("CAD", "Canadian Dollar");
        currencyNames.put("CHF", "Swiss Franc");
        currencyNames.put("CNY", "Chinese Yuan");
        currencyNames.put("INR", "Indian Rupee");
        currencyNames.put("RUB", "Russian Ruble");
        return currencyNames;
    }
}