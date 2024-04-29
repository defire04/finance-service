package com.example.financeservice.dto.account.balance;

import com.example.financeservice.model.user.User;
import com.example.financeservice.model.user.currency.Currency;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceDTO {

    @JsonView(BalanceAll.class)
    private Long id;

    @JsonView(BalanceCreateView.class)
    private BigDecimal amount;






    public interface BalanceCreateView {}
    public interface BalanceAll extends BalanceCreateView {}



}