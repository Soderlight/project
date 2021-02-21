package com.interview.project.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class ExchangeRates {
    private String table;
    private String no;
    private String effectiveDate;

    private List<Rate> rates;
}
