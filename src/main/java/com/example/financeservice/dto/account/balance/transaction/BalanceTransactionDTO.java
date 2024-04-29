package com.example.financeservice.dto.account.balance.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class BalanceTransactionDTO {

    @JsonProperty("id")
    @JsonView(BalanceTransactionAll.class)
    private Long id;

    @JsonProperty("balance_id")
    @JsonIgnore
    private Long balanceId;

    @JsonProperty("category_id")
    @JsonView(BalanceTransactionCreateView.class)
    private Long categoryId;

    @JsonProperty("amount")
    @JsonView(BalanceTransactionCreateView.class)
    private BigDecimal amount;

    @JsonProperty("transaction_date")
    @JsonView(BalanceTransactionAll.class)
    private Long transactionDate;

    public interface BalanceTransactionCreateView {
    }

    public interface BalanceTransactionAll extends BalanceTransactionCreateView {
    }
}