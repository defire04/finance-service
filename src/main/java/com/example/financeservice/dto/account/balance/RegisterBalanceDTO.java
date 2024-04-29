package com.example.financeservice.dto.account.balance;

import com.example.financeservice.model.user.currency.Currency;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RegisterBalanceDTO {

    private BigDecimal amount;

    private Currency currency;
}
