package com.example.financeservice.repository.piggy;

import com.example.financeservice.model.account.piggy.PiggyBank;
import com.example.financeservice.repository.BaseEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PiggyBankRepository extends BaseEntityRepository<PiggyBank> {
    List<PiggyBank> findAllByOwnerId(Long ownerId);
}
