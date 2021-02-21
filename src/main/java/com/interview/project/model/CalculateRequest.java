package com.interview.project.model;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class CalculateRequest {
    private Double amount;
    private String currencyFrom;
    private String currencyTo;

    public double calculate(double exRateFrom, double exRateTo)
    {
        return BigDecimal.valueOf(exRateFrom)
                .divide(BigDecimal.valueOf(exRateTo), 5, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(amount))
                .doubleValue();
    }
}
