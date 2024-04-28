package com.example.financeservice.dto.account.balance;

import com.example.financeservice.model.user.currency.Currency;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceDTO {

    @JsonView(BalanceAll.class)
    private Long id;

    @JsonView(BalanceCreateView.class)
    private BigDecimal amount;

    @JsonView(BalanceCreateView.class)
    private Currency currency;


    public interface BalanceCreateView {}
    public interface BalanceAll extends BalanceCreateView {}



}