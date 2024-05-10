package com.example.financeservice.dto.account.balance;

import com.example.financeservice.model.user.currency.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RegisterBalanceDTO {

    @Schema(description = "Amount", example = "1000.00")
    private BigDecimal amount;

    @Schema(description = "Currency", example = "UAH")
    private Currency currency;
}