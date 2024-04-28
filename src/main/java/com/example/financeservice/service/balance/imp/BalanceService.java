package com.example.financeservice.service.balance.imp;

import com.example.financeservice.model.account.balance.Balance;
import com.example.financeservice.model.user.User;
import com.example.financeservice.repository.balance.BalanceRepository;
import com.example.financeservice.service.balance.IBalanceService;
import com.example.financeservice.service.base.imp.BaseEntityService;
import com.example.financeservice.service.user.IUserService;
import org.springframework.stereotype.Service;

@Service
public class BalanceService extends BaseEntityService<Balance, BalanceRepository> implements IBalanceService {

    private final IUserService userService;

    public BalanceService(BalanceRepository repository, IUserService userService) {
        super(repository);
        this.userService = userService;
    }


    public Balance create(Balance balance, User user) {

        if (user.getBalance() != null || balance.getAmount() == null) {
            throw new RuntimeException();
        }


        Balance retunedBalance = super.create(balance);
        userService.update(user.setBalance(retunedBalance));
        return retunedBalance;


    }
}
