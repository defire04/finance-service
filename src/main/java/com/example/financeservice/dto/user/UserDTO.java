package com.example.financeservice.dto.user;

import com.example.financeservice.dto.account.balance.BalanceDTO;
import com.example.financeservice.dto.account.piggy.PiggyBankDTO;
import com.example.financeservice.model.user.currency.Currency;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private String username;
    private Long createdTimestamp;
    private String firstName;
    private String lastName;
    private String email;
    private List<PiggyBankDTO> piggyBanks;
    private BalanceDTO balance;
    private Currency currency;
}