package com.interview.project.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Rate {
    private String currency;
    private String code;
    private Double mid;

    public String getFullName() {
        return code + " - " + currency;
    }
}
