package com.example.financeservice.model.account;

import com.example.financeservice.model.BaseEntity;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseAccount extends BaseEntity {
    private BigDecimal amount;
}
