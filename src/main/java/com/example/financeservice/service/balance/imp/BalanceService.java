package com.example.financeservice.service.balance.imp;

import com.example.financeservice.exception.balance.BalanceAlreadyExitsException;
import com.example.financeservice.exception.balance.NotEnoughAmountForThisTransaction;
import com.example.financeservice.exception.category.CategoryDoesNotBelongToThisUserException;
import com.example.financeservice.exception.piggy.PiggyBankDoesNotBelongToThisUserException;
import com.example.financeservice.exception.piggy.PiggyBankDoesNotExistException;
import com.example.financeservice.model.account.balance.Balance;
import com.example.financeservice.model.account.balance.transaction.BalanceTransaction;
import com.example.financeservice.model.account.piggy.PiggyBank;
import com.example.financeservice.model.category.Category;
import com.example.financeservice.model.category.type.CategoryType;
import com.example.financeservice.model.user.User;
import com.example.financeservice.repository.balance.BalanceRepository;
import com.example.financeservice.service.balance.IBalanceService;
import com.example.financeservice.service.base.imp.BaseEntityService;
import com.example.financeservice.service.category.ICategoryService;
import com.example.financeservice.service.category.imp.CategoryService;
import com.example.financeservice.service.piggy.IPiggyService;
import com.example.financeservice.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class BalanceService extends BaseEntityService<Balance, BalanceRepository> implements IBalanceService {

    private final IUserService userService;

    private final ICategoryService categoryService;

    @Lazy
    @Autowired
    private IPiggyService piggyService;

    public BalanceService(BalanceRepository repository, IUserService userService, CategoryService categoryService) {
        super(repository);
        this.userService = userService;
        this.categoryService = categoryService;
    }


    @Transactional
    @Override
    @NonNull
    public Balance create(@NonNull Balance balance, @NonNull User user) {

        if (user.getBalance() != null || balance.getAmount() == null) {
            throw new BalanceAlreadyExitsException();
        }

        Balance retunedBalance = super.create(balance);
        userService.update(user.setBalance(retunedBalance));
        return retunedBalance;
    }


    @Transactional
    @Override
    @NonNull
    public Balance addTransaction(@NonNull User user, @NonNull BalanceTransaction transaction) {

        if (!categoryService.existsByIdAndOwnerUsername(transaction.getCategory().getId(), user.getId())) {
            throw new CategoryDoesNotBelongToThisUserException();
        }


        Optional<Category> categoryServiceById = categoryService.findById(transaction.getCategory().getId());
        Balance userBalance = user.getBalance();

        categoryServiceById.ifPresent(category -> {

            if (category.getCategoryType().equals(CategoryType.INCOME)) {
                userBalance.setAmount(userBalance.getAmount().add(transaction.getAmount()));
            } else if (category.getCategoryType().equals(CategoryType.EXPENSE)) {
                userBalance.setAmount(userBalance.getAmount().subtract(transaction.getAmount()));
            } else {
                throw new RuntimeException("Invalid category type!");
            }

            transaction.setBalance(userBalance);
            userBalance.getBalanceTransactions().add(transaction);
        });

        return repository.save(userBalance);
    }

    @Transactional
    @NonNull
    public Balance transferFromPiggyBank(@NonNull User user, Long piggyBankId, BigDecimal amount) {
        PiggyBank piggyBank = piggyService.getById(piggyBankId);
        if (piggyBank.getAmount().compareTo(amount) < 0) {
            throw new NotEnoughAmountForThisTransaction();
        }
        Balance balance = user.getBalance();
        balance.setAmount(balance.getAmount().add(amount));
        piggyBank.setAmount(piggyBank.getAmount().subtract(amount));
        piggyService.update(piggyBank);
        return balance;
    }

    @Transactional
    @NonNull
    public Balance transferToPiggyBank(@NonNull User user, Long piggyBankId, BigDecimal amount) {
        Balance balance = user.getBalance();
        if (balance.getAmount().compareTo(amount) < 0) {
            throw new NotEnoughAmountForThisTransaction();
        }
        PiggyBank piggyBank = piggyService.getById(piggyBankId);
        balance.setAmount(balance.getAmount().subtract(amount));
        piggyBank.setAmount(piggyBank.getAmount().add(amount));
        piggyService.update(piggyBank);
        return balance;
    }
}
