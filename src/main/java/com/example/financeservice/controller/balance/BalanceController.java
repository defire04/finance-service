package com.example.financeservice.controller.balance;

import com.example.financeservice.dto.account.balance.BalanceDTO;
import com.example.financeservice.mapper.balance.BalanceMapper;
import com.example.financeservice.model.account.balance.Balance;
import com.example.financeservice.model.user.User;
import com.example.financeservice.service.balance.IBalanceService;
import com.example.financeservice.service.balance.imp.BalanceService;
import com.example.financeservice.service.user.IUserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/balance")
public class BalanceController {

    private final BalanceService balanceService;
    private final IUserService userService;

    private final BalanceMapper balanceMapper;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @JsonView(BalanceDTO.BalanceCreateView.class) BalanceDTO balanceDTO,
                       Principal principal) {
        userService.findByUsername(principal.getName()).ifPresent(user -> {

            user.setCurrency(balanceDTO.getCurrency());
            balanceService.create(balanceMapper.toModel(balanceDTO), user);
        });


    }
}
