package com.example.financeservice.controller.balance;

import com.example.financeservice.dto.account.balance.BalanceDTO;
import com.example.financeservice.dto.account.balance.RegisterBalanceDTO;
import com.example.financeservice.dto.account.balance.transaction.BalanceTransactionDTO;
import com.example.financeservice.dto.response.ResponseDTO;
import com.example.financeservice.exception.balance.BalanceNotRegisterException;
import com.example.financeservice.exception.user.UserPrincipalNotFoundException;
import com.example.financeservice.mapper.balance.BalanceMapper;
import com.example.financeservice.mapper.balance.transaction.BalanceTransactionMapper;
import com.example.financeservice.model.account.balance.transaction.BalanceTransaction;
import com.example.financeservice.service.balance.imp.BalanceService;
import com.example.financeservice.service.balance.transaction.imp.BalanceTransactionService;
import com.example.financeservice.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/balances")
public class BalanceController {

    private final BalanceService balanceService;
    private final IUserService userService;

    private final BalanceMapper balanceMapper;
    private final BalanceTransactionMapper balanceTransactionMapper;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<BalanceDTO> create(@RequestBody RegisterBalanceDTO balanceDTO,
                                          Principal principal) {

        return userService.findByUsername(principal.getName())
                .map(user -> {
                    user.setCurrency(balanceDTO.getCurrency());
                    return ResponseDTO.<BalanceDTO>builder()
                            .data(balanceMapper.toDTO(balanceService.create(balanceMapper.toModel(balanceDTO), user)))
                            .build();
                })
                .orElseThrow(UserPrincipalNotFoundException::new);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<BalanceDTO> balance(Principal principal) {

        return userService.findByUsername(principal.getName())
                .map(user -> new ResponseDTO<>(balanceMapper.toDTO(user.getBalance())))
                .orElseThrow(BalanceNotRegisterException::new);

    }

    @PostMapping("/transaction")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<BalanceDTO> balance(@RequestBody BalanceTransactionDTO balanceTransactionDTO,
                                           Principal principal) {
        return userService.findByUsername(principal.getName())
                .map(user -> {
                    BalanceTransaction transaction = balanceTransactionMapper.toModel(balanceTransactionDTO);
                    return new ResponseDTO<>(balanceMapper.toDTO(balanceService.addTransaction(user, transaction)));
                })
                .orElseThrow(UserPrincipalNotFoundException::new);


    }


}
