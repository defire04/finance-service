package com.example.financeservice.model.category;

import com.example.financeservice.model.BaseEntity;
import com.example.financeservice.model.account.balance.transaction.BalanceTransaction;
import com.example.financeservice.model.category.type.CategoryType;
import com.example.financeservice.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "category")
public class Category extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "category_type", nullable = false)
    private CategoryType categoryType;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_owner_id", nullable = false)
    @JsonIgnore
    private User owner;

}
