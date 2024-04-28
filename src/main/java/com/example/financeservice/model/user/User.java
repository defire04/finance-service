package com.example.financeservice.model.user;

import com.example.financeservice.model.BaseEntity;
import com.example.financeservice.model.account.balance.Balance;
import com.example.financeservice.model.account.piggy.PiggyBank;
import com.example.financeservice.model.category.Category;
import com.example.financeservice.model.user.currency.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@ToString
@DynamicUpdate
@Accessors(chain = true)
@Table(name = "service_user")
public class User extends BaseEntity {

    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<PiggyBank> piggyBanks = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Category> categories;

    @OneToOne()
    @JoinColumn(name = "balance_id")
    private Balance balance;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "created_timestamp")
    private Long createdTimestamp;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;


}