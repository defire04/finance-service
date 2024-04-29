package com.example.financeservice.service.balance.imp;

import com.example.financeservice.exception.balance.BalanceAlreadyExitsException;
import com.example.financeservice.exception.category.CategoryDoesNotBelongToThisUserException;
import com.example.financeservice.model.account.balance.Balance;
import com.example.financeservice.model.account.balance.transaction.BalanceTransaction;
import com.example.financeservice.model.category.Category;
import com.example.financeservice.model.category.type.CategoryType;
import com.example.financeservice.model.user.User;
import com.example.financeservice.repository.balance.BalanceRepository;
import com.example.financeservice.service.balance.IBalanceService;
import com.example.financeservice.service.base.imp.BaseEntityService;
import com.example.financeservice.service.category.imp.CategoryService;
import com.example.financeservice.service.user.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class BalanceService extends BaseEntityService<Balance, BalanceRepository> implements IBalanceService {

    private final IUserService userService;

    private final CategoryService categoryService;

    public BalanceService(BalanceRepository repository, IUserService userService, CategoryService categoryService) {
        super(repository);
        this.userService = userService;
        this.categoryService = categoryService;
    }


    @Transactional
    public Balance create(Balance balance, User user) {

        if (user.getBalance() != null || balance.getAmount() == null) {
            throw new BalanceAlreadyExitsException();
        }

        Balance retunedBalance = super.create(balance);
        userService.update(user.setBalance(retunedBalance));
        return retunedBalance;
    }


    @Transactional
    public Balance addTransaction(User user, BalanceTransaction transaction) {

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
}
