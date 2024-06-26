package com.example.financeservice.model.account.balance.transaction;

import com.example.financeservice.model.BaseEntity;
import com.example.financeservice.model.account.balance.Balance;
import com.example.financeservice.model.category.Category;
import com.example.financeservice.model.category.type.CategoryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@ToString
@Accessors(chain = true)
@Table(name = "balance_transaction")
public class BalanceTransaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "balance_id", nullable = false)
    private Balance balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Transient
    private CategoryType categoryType;

    @Transient
    private String categoryName;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "transaction_date", nullable = false)
    private Long transactionDate;

    @PrePersist
    private void prePersist() {
        transactionDate = new Date().getTime();
    }

    public CategoryType getCategoryType() {
        return category.getCategoryType();
    }

    public String getCategoryName() {
        return category.getName();
    }
}
