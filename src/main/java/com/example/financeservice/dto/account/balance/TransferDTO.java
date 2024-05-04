package com.example.financeservice.dto.account.balance;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class TransferDTO {

    private Long piggyBankId;
    private BigDecimal amount;
}
