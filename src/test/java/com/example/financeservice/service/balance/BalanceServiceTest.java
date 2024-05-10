package com.example.financeservice.service.balance;

import com.example.financeservice.exception.balance.BalanceAlreadyExitsException;
import com.example.financeservice.exception.category.CategoryDoesNotBelongToThisUserException;
import com.example.financeservice.model.account.balance.Balance;
import com.example.financeservice.model.account.balance.transaction.BalanceTransaction;
import com.example.financeservice.model.category.Category;
import com.example.financeservice.model.category.type.CategoryType;
import com.example.financeservice.model.user.User;
import com.example.financeservice.repository.balance.BalanceRepository;
import com.example.financeservice.service.balance.imp.BalanceService;
import com.example.financeservice.service.category.imp.CategoryService;
import com.example.financeservice.service.piggy.imp.PiggyService;
import com.example.financeservice.service.user.IUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BalanceServiceTest {

    @Mock
    private BalanceRepository balanceRepository;

    @Mock
    private IUserService userService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private PiggyService piggyService;

    @InjectMocks
    private BalanceService balanceService;


    @Test
    @DisplayName("Test create method with valid balance and user")
    public void testCreate_ValidBalanceAndUser_Success() {
        User user = new User();
        Balance balance = new Balance();
        balance.setAmount(BigDecimal.valueOf(100));

        when(balanceRepository.save(balance)).thenReturn(balance);
        Balance createdBalance = balanceService.create(balance, user);

        verify(balanceRepository, times(1)).save(balance);

        verify(userService, times(1)).update(any(User.class));
        verify(userService, times(1)).update(argThat(updatedUser -> updatedUser.getBalance() == createdBalance));
    }

    @Test
    @DisplayName("Test create method with user already having a balance")
    public void testCreate_UserAlreadyHasBalance_ExceptionThrown() {
        User user = new User();
        Balance balance = new Balance();
        user.setBalance(new Balance());

        assertThrows(BalanceAlreadyExitsException.class, () -> balanceService.create(balance, user));

        verify(balanceRepository, never()).save(balance);

        verify(userService, never()).update(any(User.class));
    }

    @Test
    @DisplayName("Test addTransaction method with valid category and user")
    public void testAddTransaction_ValidCategoryAndUser_Success() {

        User user = new User();
        Balance balance = new Balance();
        balance.setAmount(BigDecimal.valueOf(100));
        user.setBalance(balance);
        BalanceTransaction transaction = new BalanceTransaction();
        transaction.setAmount(BigDecimal.TEN);
        Category category = new Category();
        category.setId(1L);
        category.setCategoryType(CategoryType.EXPENSE);
        transaction.setCategory(category);

        when(categoryService.existsByIdAndOwnerUsername(category.getId(), user.getId())).thenReturn(true);
        when(categoryService.findById(category.getId())).thenReturn(Optional.of(category));

        balanceService.addTransaction(user, transaction);

        verify(balanceRepository, times(1)).save(balance);
    }

    @Test
    @DisplayName("Test addTransaction method with invalid category")
    public void testAddTransaction_InvalidCategory_ExceptionThrown() {
        User user = new User();
        Balance balance = new Balance();
        balance.setAmount(BigDecimal.valueOf(100));
        user.setBalance(balance);
        BalanceTransaction transaction = new BalanceTransaction();
        Category category = new Category();
        category.setId(1L);
        category.setCategoryType(CategoryType.EXPENSE);
        transaction.setCategory(category);

        when(categoryService.existsByIdAndOwnerUsername(category.getId(), user.getId())).thenReturn(false);

        assertThrows(CategoryDoesNotBelongToThisUserException.class, () -> balanceService.addTransaction(user, transaction));

        verify(balanceRepository, never()).save(balance);
    }


}