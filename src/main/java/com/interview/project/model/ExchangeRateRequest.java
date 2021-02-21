package com.interview.project.model;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ExchangeRateRequest {
    private List<Currency> currencies;

    public List<String> getCurrencyCodes()
    {
        return currencies.stream()
                .map(Currency::getName)
                .collect(Collectors.toList());
    }
}
