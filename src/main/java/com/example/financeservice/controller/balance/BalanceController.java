package com.example.financeservice.controller.balance;

import com.example.financeservice.dto.account.balance.BalanceDTO;
import com.example.financeservice.dto.account.balance.RegisterBalanceDTO;
import com.example.financeservice.dto.account.balance.TransferDTO;
import com.example.financeservice.dto.account.balance.transaction.BalanceTransactionDTO;
import com.example.financeservice.dto.response.ResponseDTO;
import com.example.financeservice.exception.balance.BalanceNotRegisterException;
import com.example.financeservice.exception.user.UserPrincipalNotFoundException;
import com.example.financeservice.mapper.balance.BalanceMapper;
import com.example.financeservice.mapper.balance.transaction.BalanceTransactionMapper;
import com.example.financeservice.model.account.balance.Balance;
import com.example.financeservice.model.account.balance.transaction.BalanceTransaction;
import com.example.financeservice.service.balance.IBalanceService;
import com.example.financeservice.service.balance.imp.BalanceService;
import com.example.financeservice.service.user.IUserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/balances")
public class BalanceController {

    private final IBalanceService balanceService;
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

    @PostMapping("/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<BalanceDTO> createTransaction
            (@RequestBody @JsonView(BalanceTransactionDTO.BalanceTransactionCreateView.class) BalanceTransactionDTO balanceTransactionDTO,
             Principal principal) {
        return userService.findByUsername(principal.getName())
                .map(user -> {
                    BalanceTransaction transaction = balanceTransactionMapper.toModel(balanceTransactionDTO);
                    return new ResponseDTO<>(balanceMapper.toDTO(balanceService.addTransaction(user, transaction)));
                })
                .orElseThrow(UserPrincipalNotFoundException::new);
    }

    @GetMapping("/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<List<BalanceTransactionDTO>> getTransactionByUser(Principal principal) {
        return userService.findByUsername(principal.getName())
                .map(user -> new ResponseDTO<>(user.getBalance().getBalanceTransactions().stream().map(balanceTransactionMapper::toDTO).toList()))
                .orElseThrow(UserPrincipalNotFoundException::new);
    }


    @PostMapping("/transfer/from-piggy-bank")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<BalanceDTO> transferFromPiggy(@RequestBody TransferDTO transferDTO,
                                                     Principal principal) {
        return userService.findByUsername(principal.getName())
                .map(user -> {
                    Balance balance = balanceService.transferFromPiggyBank(user, transferDTO.getPiggyBankId(), transferDTO.getAmount());
                    return new ResponseDTO<>(balanceMapper.toDTO(balance));
                })
                .orElseThrow(UserPrincipalNotFoundException::new);
    }

    @PostMapping("/transfer/to-piggy-bank")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<BalanceDTO> transferToPiggy(@RequestBody TransferDTO transferDTO,
                                                   Principal principal) {
        return userService.findByUsername(principal.getName())
                .map(user -> {
                    Balance balance = balanceService.transferToPiggyBank(user, transferDTO.getPiggyBankId(), transferDTO.getAmount());
                    return new ResponseDTO<>(balanceMapper.toDTO(balance));
                })
                .orElseThrow(UserPrincipalNotFoundException::new);
    }

}
