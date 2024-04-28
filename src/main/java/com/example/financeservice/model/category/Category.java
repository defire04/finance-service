package com.example.financeservice.model.category;

import com.example.financeservice.model.BaseEntity;
import com.example.financeservice.model.category.type.CategoryType;
import com.example.financeservice.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@ToString
@Accessors(chain = true)
@Table(name = "balance_transaction")
public class Category extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "category_type", nullable = false)
    private CategoryType categoryType;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_owner_id")
    private User owner;
}
