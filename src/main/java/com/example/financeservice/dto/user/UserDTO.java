package com.example.financeservice.dto.user;

import com.example.financeservice.dto.account.balance.BalanceDTO;
import com.example.financeservice.dto.account.piggy.PiggyBankDTO;
import com.example.financeservice.model.user.currency.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    @Schema(description = "Username")
    private String username;

    @Schema(description = "Timestamp when the user was created")
    private Long createdTimestamp;

    @Schema(description = "First name")
    private String firstName;

    @Schema(description = "Last name")
    private String lastName;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "List of piggy banks associated with the user")
    private List<PiggyBankDTO> piggyBanks;

    @Schema(description = "User's balance")
    private BalanceDTO balance;

    @Schema(description = "User's currency")
    private Currency currency;
}