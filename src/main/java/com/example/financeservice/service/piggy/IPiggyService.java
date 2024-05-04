package com.example.financeservice.service.piggy;

import com.example.financeservice.exception.piggy.PiggyBankDoesNotBelongToThisUserException;
import com.example.financeservice.model.account.piggy.PiggyBank;
import com.example.financeservice.service.base.IBaseEntityService;
import jakarta.transaction.Transactional;
import org.springframework.lang.NonNull;

import java.util.List;

public interface IPiggyService extends IBaseEntityService<PiggyBank> {
    /**
     * Retrieves all piggy banks owned by the specified user.
     *
     * @param ownerId The ID of the owner user.
     * @return A list of piggy banks owned by the specified user.
     */
    @NonNull
    List<PiggyBank> getAllByOwnerId(Long ownerId);


    /**
     * Updates some field of the provided piggy bank.
     *
     * @param piggyBank The piggy bank to be updated.
     * @return The updated piggy bank entity.
     */
    @NonNull
    PiggyBank updateSomeField(@NonNull PiggyBank piggyBank);

    /**
     * Updates all information of the provided piggy bank.
     *
     * @param piggyBank The piggy bank to be updated.
     * @return The updated piggy bank entity.
     * @throws PiggyBankDoesNotBelongToThisUserException If the provided piggy bank does not belong to the current user.
     */
    @NonNull
    PiggyBank update(@NonNull PiggyBank piggyBank);

    /**
     * Deletes the specified piggy bank and transfers its amount back to the user's balance.
     *
     * @param piggyBank The piggy bank to be deleted.
     * @throws PiggyBankDoesNotBelongToThisUserException If the provided piggy bank does not belong to the current user.
     */
    @Transactional
    void delete(@NonNull PiggyBank piggyBank);

    @NonNull
    PiggyBank getById(Long piggyBankId);
}
