package com.example.financeservice.service.piggy;

import com.example.financeservice.exception.piggy.PiggyBankDoesNotBelongToThisUserException;
import com.example.financeservice.exception.piggy.PiggyBankDoesNotExistException;
import com.example.financeservice.model.account.piggy.PiggyBank;
import com.example.financeservice.model.user.User;
import com.example.financeservice.repository.piggy.PiggyBankRepository;
import com.example.financeservice.service.balance.IBalanceService;
import com.example.financeservice.service.piggy.imp.PiggyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PiggyServiceTest {

    @Mock
    private PiggyBankRepository piggyBankRepository;

    @InjectMocks
    private PiggyService piggyService;

    @Mock
    private IBalanceService balanceService;


    @DisplayName("Get all Piggy Banks by Owner ID")
    @Test
    public void testGetAllByOwnerId() {
        Long ownerId = 1L;
        User owner = new User();
        owner.setId(ownerId);

        PiggyBank piggyBank1 = new PiggyBank();
        piggyBank1.setId(1L);
        piggyBank1.setName("Piggy Bank 1");
        piggyBank1.setPurpose("Saving for vacation");
        piggyBank1.setOwner(owner);

        PiggyBank piggyBank2 = new PiggyBank();
        piggyBank2.setId(2L);
        piggyBank2.setName("Piggy Bank 2");
        piggyBank2.setPurpose("Saving for a new car");
        piggyBank2.setOwner(owner);

        List<PiggyBank> expectedPiggyBanks = Arrays.asList(piggyBank1, piggyBank2);

        when(piggyBankRepository.findAllByOwnerId(ownerId)).thenReturn(expectedPiggyBanks);

        List<PiggyBank> resultPiggyBanks = piggyService.getAllByOwnerId(ownerId);

        verify(piggyBankRepository).findAllByOwnerId(ownerId);

        assertEquals(expectedPiggyBanks, resultPiggyBanks);
    }

    @DisplayName("Get Piggy Bank By Id - Successful")
    @Test
    public void testGetById_Successful() {
        Long piggyBankId = 1L;
        PiggyBank piggyBank = new PiggyBank();
        piggyBank.setId(piggyBankId);

        when(piggyBankRepository.findById(piggyBankId)).thenReturn(Optional.of(piggyBank));

        PiggyBank retrievedPiggyBank = piggyService.getById(piggyBankId);

        verify(piggyBankRepository).findById(piggyBankId);

        assertEquals(piggyBank, retrievedPiggyBank);
    }

    @DisplayName("Get Piggy Bank By Id - Not Found")
    @Test
    public void testGetById_NotFound() {
        Long piggyBankId = 1L;

        when(piggyBankRepository.findById(piggyBankId)).thenReturn(Optional.empty());

        assertThrows(PiggyBankDoesNotExistException.class, () -> {
            piggyService.getById(piggyBankId);
        });
    }


    @Test
    @DisplayName("Throw exception when piggy bank does not belong to this user")
    void updateSomeField_PiggyBankDoesNotBelongToThisUserExceptionThrown() {

        PiggyBank existingPiggyBank = new PiggyBank();
        existingPiggyBank.setId(1L);
        existingPiggyBank.setName("My Piggy");
        existingPiggyBank.setPurpose("Saving");
        User user = new User();
        user.setId(1L);

        existingPiggyBank.setOwner(user);

        PiggyBank updatedPiggyBank = new PiggyBank();
        updatedPiggyBank.setId(1L);
        updatedPiggyBank.setName("Updated Piggy");
        updatedPiggyBank.setPurpose("Updated purpose");
        User user2 = new User();
        user.setId(2L);
        updatedPiggyBank.setOwner(user2);

        when(piggyBankRepository.findById(anyLong())).thenReturn(Optional.of(existingPiggyBank));

        assertThrows(PiggyBankDoesNotBelongToThisUserException.class, () -> {
            piggyService.updateSomeField(updatedPiggyBank);
        });

        verify(piggyBankRepository, times(1)).findById(anyLong());
        verify(piggyBankRepository, never()).save(any(PiggyBank.class));
    }

    @Test
    @DisplayName("Successful update of piggy bank")
    void updateSomeField_SuccessfulUpdate() {
        // Arrange
        PiggyBank existingPiggyBank = new PiggyBank();
        existingPiggyBank.setId(1L);
        existingPiggyBank.setAmount(BigDecimal.valueOf(100.0));
        User user = new User();
        user.setId(1L);
        existingPiggyBank.setOwner(user);

        PiggyBank updatedPiggyBank = new PiggyBank();
        updatedPiggyBank.setId(1L);
        updatedPiggyBank.setName("Updated Piggy");
        updatedPiggyBank.setPurpose("Updated purpose");
        updatedPiggyBank.setOwner(user);

        when(piggyBankRepository.findById(anyLong())).thenReturn(Optional.of(existingPiggyBank));
        when(piggyBankRepository.save(any(PiggyBank.class))).thenReturn(updatedPiggyBank);

        PiggyBank result = piggyService.updateSomeField(updatedPiggyBank);

        assertEquals("Updated Piggy", result.getName());
        assertEquals("Updated purpose", result.getPurpose());
        assertEquals(BigDecimal.valueOf(100.0), result.getAmount());

        verify(piggyBankRepository, times(2)).findById(anyLong());
        verify(piggyBankRepository, times(1)).save(any(PiggyBank.class));
    }

    @Test
    @DisplayName("Throw exception when piggy bank does not belong to this user")
    void update_PiggyBankDoesNotBelongToThisUserExceptionThrown() {
        PiggyBank existingPiggyBank = new PiggyBank();
        existingPiggyBank.setId(1L);
        User user = new User();
        user.setId(1L);
        existingPiggyBank.setOwner(user);

        PiggyBank updatedPiggyBank = new PiggyBank();
        updatedPiggyBank.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        updatedPiggyBank.setOwner(user2);

        when(piggyBankRepository.findById(anyLong())).thenReturn(Optional.of(existingPiggyBank));

        assertThrows(PiggyBankDoesNotBelongToThisUserException.class, () -> {
            piggyService.update(updatedPiggyBank);
        });

        verify(piggyBankRepository, times(1)).findById(anyLong());
        verify(piggyBankRepository, never()).save(any(PiggyBank.class));
    }

    @Test
    @DisplayName("Successful update of piggy bank")
    void update_SuccessfulUpdate() {
        PiggyBank existingPiggyBank = new PiggyBank();
        existingPiggyBank.setId(1L);
        existingPiggyBank.setAmount(BigDecimal.valueOf(100.0));
        User user = new User();
        user.setId(1L);
        existingPiggyBank.setOwner(user);

        PiggyBank updatedPiggyBank = new PiggyBank();
        updatedPiggyBank.setId(1L);
        updatedPiggyBank.setName("Updated Piggy");
        updatedPiggyBank.setPurpose("Updated purpose");
        updatedPiggyBank.setOwner(user);
        updatedPiggyBank.setAmount(BigDecimal.valueOf(100.0));

        when(piggyBankRepository.findById(anyLong())).thenReturn(Optional.of(existingPiggyBank));
        when(piggyBankRepository.save(any(PiggyBank.class))).thenReturn(updatedPiggyBank);

        PiggyBank result = piggyService.update(updatedPiggyBank);

        assertEquals("Updated Piggy", result.getName());
        assertEquals("Updated purpose", result.getPurpose());
        assertEquals(BigDecimal.valueOf(100.0), result.getAmount());

        verify(piggyBankRepository, times(1)).findById(anyLong());
        verify(piggyBankRepository, times(1)).save(any(PiggyBank.class));
    }



    @Test
    @DisplayName("Delete piggy bank belonging to the user and has no amount")
    void delete_PiggyBankBelongsToUserAndHasNoAmount() {
        PiggyBank piggyBank = new PiggyBank();
        piggyBank.setId(1L);
        piggyBank.setAmount(null);

        User user = new User();
        user.setId(1L);
        piggyBank.setOwner(user);

        PiggyBank existingPiggyBank = new PiggyBank();
        existingPiggyBank.setOwner(user);

        when(piggyBankRepository.findById(anyLong())).thenReturn(Optional.of(existingPiggyBank));

        piggyService.delete(piggyBank);

        verify(balanceService, never()).update(any());
        verify(piggyBankRepository, times(1)).delete(piggyBank);
    }

    @Test
    @DisplayName("Throw exception when piggy bank does not belong to the user")
    void delete_PiggyBankDoesNotBelongToUser() {

        PiggyBank piggyBank = new PiggyBank();
        piggyBank.setId(1L);

        User user1 = new User();
        user1.setId(1L);
        piggyBank.setOwner(user1);

        User user2 = new User();
        user2.setId(2L);

        PiggyBank existingPiggyBank = new PiggyBank();
        existingPiggyBank.setOwner(user2);

        when(piggyBankRepository.findById(anyLong())).thenReturn(Optional.of(existingPiggyBank));


        assertThrows(PiggyBankDoesNotBelongToThisUserException.class, () -> {
            piggyService.delete(piggyBank);
        });


        verify(balanceService, never()).update(any());
        verify(piggyBankRepository, never()).delete(piggyBank);
    }

}