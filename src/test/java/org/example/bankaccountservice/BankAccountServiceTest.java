package org.example.bankaccountservice;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

public class BankAccountServiceTest {


    @Test
    public void testGenerateNewAccountId_FirstAccount() {
        // Mock repository
        BankAccountRepository repository = Mockito.mock(BankAccountRepository.class);
        when(repository.findTopByOrderByAccountIdDesc()).thenReturn(Optional.empty());

        // Service
        BankAccountService service = new BankAccountService(repository);
        String newId = service.generateNewAccountId();

        // Assert
        assertEquals("K-0001", newId);
    }

    @Test
    public void testGenerateNewAccountId_ExistingAccounts() {
        // Mock repository
        BankAccountRepository repository = Mockito.mock(BankAccountRepository.class);
        BankAccount lastAccount = new BankAccount("K-0045", "Test User", 100.0);
        when(repository.findTopByOrderByAccountIdDesc()).thenReturn(Optional.of(lastAccount));

        // Service
        BankAccountService service = new BankAccountService(repository);
        String newId = service.generateNewAccountId();

        // Assert
        assertEquals("K-0046", newId);
    }
}
