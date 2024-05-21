package com.example.financeservice.dto.account.balance;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceDTO {

    @Schema(description = "Balance ID")
    @JsonView(BalanceAll.class)
    private Long id;

    @Schema(description = "Balance amount")
    @JsonView(BalanceCreateView.class)
    private BigDecimal amount;

    @Schema(description = "Income all time amount")
    @JsonView(BalanceAll.class)
    private BigDecimal incomeAmount;

    @Schema(description = "Expense all time amount")
    @JsonView(BalanceAll.class)
    private BigDecimal expenseAmount;


    public interface BalanceCreateView {
    }

    public interface BalanceAll extends BalanceCreateView {
    }


}