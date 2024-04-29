package com.example.financeservice.service.balance.transaction.imp;

import com.example.financeservice.model.account.balance.transaction.BalanceTransaction;
import com.example.financeservice.repository.balance.transaction.BalanceTransactionRepository;
import com.example.financeservice.service.balance.transaction.IBalanceTransactionService;
import com.example.financeservice.service.base.imp.BaseEntityService;
import org.springframework.stereotype.Service;

@Service
public class BalanceTransactionService extends BaseEntityService<BalanceTransaction, BalanceTransactionRepository> implements IBalanceTransactionService {
    public BalanceTransactionService(BalanceTransactionRepository repository) {
        super(repository);
    }
}
