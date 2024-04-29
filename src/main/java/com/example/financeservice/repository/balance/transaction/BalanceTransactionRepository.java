package com.example.financeservice.repository.balance.transaction;

import com.example.financeservice.model.account.balance.transaction.BalanceTransaction;
import com.example.financeservice.repository.BaseEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceTransactionRepository extends BaseEntityRepository<BalanceTransaction> {
}
