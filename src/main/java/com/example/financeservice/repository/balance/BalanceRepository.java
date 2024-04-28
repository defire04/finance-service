package com.example.financeservice.repository.balance;

import com.example.financeservice.model.account.balance.Balance;
import com.example.financeservice.repository.BaseEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends BaseEntityRepository<Balance> {
}
