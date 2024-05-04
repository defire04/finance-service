package com.example.financeservice.service.balance;

import com.example.financeservice.exception.balance.BalanceAlreadyExitsException;
import com.example.financeservice.exception.balance.NotEnoughAmountForThisTransaction;
import com.example.financeservice.exception.category.CategoryDoesNotBelongToThisUserException;
import com.example.financeservice.model.account.balance.Balance;
import com.example.financeservice.model.account.balance.transaction.BalanceTransaction;
import com.example.financeservice.model.user.User;
import com.example.financeservice.service.base.IBaseEntityService;
import jakarta.transaction.Transactional;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

public interface IBalanceService extends IBaseEntityService<Balance> {

    /**
     * Creates a new balance for a user.
     *
     * @param balance The balance to create
     * @param user    The user for whom the balance is created
     * @return The created balance
     * @throws BalanceAlreadyExitsException If the user already has a balance or the balance amount is not provided
     */
    @NonNull
    Balance create(@NonNull Balance balance, @NonNull User user);

    /**
     * Adds a transaction to the user's balance.
     *
     * @param user        The user to add the transaction to
     * @param transaction The transaction to add
     * @return The updated user balance after adding the transaction
     * @throws CategoryDoesNotBelongToThisUserException If the transaction category does not belong to the user
     */
    @NonNull
    Balance addTransaction(@NonNull User user, @NonNull BalanceTransaction transaction);

    /**
     * Transfers funds from the specified piggy bank to the user's balance.
     *
     * @param user        The user performing the transaction.
     * @param piggyBankId The ID of the piggy bank from which funds will be transferred.
     * @param amount      The amount of funds to transfer.
     * @return The updated balance of the user after the transaction.
     * @throws NotEnoughAmountForThisTransaction if there are insufficient funds in the piggy bank.
     */
    @Transactional
    @NonNull
    Balance transferFromPiggyBank(User user, Long piggyBankId, BigDecimal amount);

    /**
     * Transfers funds from the user's balance to the specified piggy bank.
     *
     * @param user        The user performing the transaction.
     * @param piggyBankId The ID of the piggy bank to which funds will be transferred.
     * @param amount      The amount of funds to transfer.
     * @return The updated balance of the user after the transaction.
     * @throws NotEnoughAmountForThisTransaction if there are insufficient funds in the user's balance.
     */
    @Transactional
    @NonNull
    Balance transferToPiggyBank(User user, Long piggyBankId, BigDecimal amount);
}
