package com.example.financeservice.model.user.currency;

import lombok.Getter;

@Getter
public enum Currency {
    EUR("EUR"),
    UAH("UAH"),
    USD("USD");

    private final String code;

    Currency(String code) {
        this.code = code;
    }

}
