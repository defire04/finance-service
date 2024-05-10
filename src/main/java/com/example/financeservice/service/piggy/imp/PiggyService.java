package com.example.financeservice.service.piggy.imp;

import com.example.financeservice.exception.piggy.PiggyBankDoesNotBelongToThisUserException;
import com.example.financeservice.exception.piggy.PiggyBankDoesNotExistException;
import com.example.financeservice.model.account.balance.Balance;
import com.example.financeservice.model.account.piggy.PiggyBank;
import com.example.financeservice.model.user.User;
import com.example.financeservice.repository.piggy.PiggyBankRepository;
import com.example.financeservice.service.balance.IBalanceService;
import com.example.financeservice.service.base.imp.BaseEntityService;
import com.example.financeservice.service.piggy.IPiggyService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service
public class PiggyService extends BaseEntityService<PiggyBank, PiggyBankRepository> implements IPiggyService {


    private final IBalanceService balanceService;
    public PiggyService(PiggyBankRepository repository, IBalanceService balanceService) {
        super(repository);
        this.balanceService = balanceService;
    }



    @Override
    @NonNull
    public List<PiggyBank> getAllByOwnerId(Long ownerId) {
        return repository.findAllByOwnerId(ownerId);
    }

    @Override
    @NonNull
    public PiggyBank updateSomeField(@NonNull PiggyBank piggyBank) {
        checkPiggyBankOwnership(piggyBank);
        piggyBank.setAmount(getById(piggyBank.getId()).getAmount());
        return super.update(piggyBank);
    }
    @Override
    @NonNull
    public PiggyBank update(@NonNull PiggyBank piggyBank) {
        checkPiggyBankOwnership(piggyBank);
        return super.update(piggyBank);
    }

    @Override
    @NonNull
    @Transactional
    public void delete(@NonNull PiggyBank piggyBank) {
        checkPiggyBankOwnership(piggyBank);

        if (piggyBank.getAmount() != null) {
            PiggyBank existingPiggyBank = getById(piggyBank.getId());
            Balance balance = existingPiggyBank.getOwner().getBalance();
            balance.setAmount(balance.getAmount().add(existingPiggyBank.getAmount()));
            balanceService.update(balance);
        }

        super.delete(piggyBank);
    }

    @NonNull
    public PiggyBank getById(Long id) {
        return repository.findById(id)
                .orElseThrow(PiggyBankDoesNotExistException::new);
    }

    private void checkPiggyBankOwnership(@NonNull PiggyBank piggyBank) {

        PiggyBank existingPiggyBank = getById(piggyBank.getId());
        User existingOwner = existingPiggyBank.getOwner();
        User newOwner = piggyBank.getOwner();
        if (!Objects.equals(existingOwner.getId(), newOwner.getId())) {
            throw new PiggyBankDoesNotBelongToThisUserException();
        }
    }


}
