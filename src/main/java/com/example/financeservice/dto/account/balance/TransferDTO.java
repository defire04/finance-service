package com.example.financeservice.dto.account.balance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class TransferDTO {

    @JsonProperty("piggy_bank_id")
    private Long piggyBankId;
    private BigDecimal amount;
}
