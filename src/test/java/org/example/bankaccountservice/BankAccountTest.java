package org.example.bankaccountservice;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BankAccountTest {

    @Test
    public void testBankAccountCreation() {
        BankAccount account = new BankAccount("K-0001", "Max Musterman", 100.0);
        assertEquals("K-0001", account.getAccountId());
        assertEquals("Max Musterman", account.getOwnerName());
        assertEquals(100.0, account.getBalance());
        assertFalse(account.isDeleted());
        assertNotNull(account.getCreatedAt());
        assertNull(account.getDeletedAt());
    }

    @Test
    public void testSoftDelete() {
        BankAccount account = new BankAccount("K-0002", "Anna Beispiel", 200.0);
        account.setDeleted(true);
        assertTrue(account.isDeleted());
        assertNotNull(account.getDeletedAt());
    }
}
