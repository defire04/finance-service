package com.example.financeservice.model.account.balance;

import com.example.financeservice.model.account.BaseAccount;
import com.example.financeservice.model.account.balance.transaction.BalanceTransaction;
import com.example.financeservice.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

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
}
