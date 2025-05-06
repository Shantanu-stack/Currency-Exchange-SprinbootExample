package com.example.currency_exchange;


public class CurrencyConversionRequest {
    private String fromCurrency;
    private String toCurrency;
    private double amount;

    // Getter & Setter for fromCurrency
    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    // Getter & Setter for toCurrency
    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    // Getter & Setter for amount
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
