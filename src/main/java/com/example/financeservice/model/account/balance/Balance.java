package com.example.financeservice.model.account.balance;

import com.example.financeservice.model.account.BaseAccount;
import com.example.financeservice.model.account.balance.transaction.BalanceTransaction;
import com.example.financeservice.model.category.type.CategoryType;
import com.example.financeservice.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@ToString
@Accessors(chain = true)
@Table(name = "balance")
public class Balance extends BaseAccount {

    @OneToOne(mappedBy = "balance")
    @JoinColumn(name = "user_owner_id")
    @JsonIgnore
    private User owner;

    @OneToMany(mappedBy = "balance", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<BalanceTransaction> balanceTransactions = new ArrayList<>();

    @Transient
    private BigDecimal incomeAmount;

    @Transient
    private BigDecimal expenseAmount;

    public BigDecimal getIncomeAmount() {
        return balanceTransactions.stream()
                .filter(tx -> tx.getCategory().getCategoryType() == CategoryType.INCOME)
                .map(BalanceTransaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getExpenseAmount() {
        return balanceTransactions.stream()
                .filter(tx -> tx.getCategory().getCategoryType() == CategoryType.EXPENSE)
                .map(BalanceTransaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
