package org.example.bankaccountservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // PostgreSQL verwende
public class BankAccountRepositoryTest {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Test
    public void testSaveAndFindById() {
        // Create and save a new BankAccount
        BankAccount account = new BankAccount("K-0001", "Alice", 100.0);
        bankAccountRepository.save(account);

        // Retrieve the account by ID
        BankAccount retrievedAccount = bankAccountRepository.findById("K-0001").orElse(null);

        // Verify the account was saved and retrieved correctly
        assertNotNull(retrievedAccount);
        assertEquals("Alice", retrievedAccount.getOwnerName());
        assertEquals(100.0, retrievedAccount.getBalance());
    }

    @Test
    public void testDelete() {
        // Create and save a new BankAccount
        BankAccount account = new BankAccount("K-0002", "Bob", 200.0);
        bankAccountRepository.save(account);

        // Verify the account is in the database
        assertTrue(bankAccountRepository.findById("K-0002").isPresent());

        // Delete the account
        bankAccountRepository.deleteById("K-0002");

        // Verify the account was deleted
        assertFalse(bankAccountRepository.findById("K-0002").isPresent());
    }
}
