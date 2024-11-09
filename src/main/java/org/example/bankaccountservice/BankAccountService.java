package org.example.bankaccountservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Method creates a new bank account
     * @param ownerName
     * @param initialBalance
     * @return new bank account
     */
    public BankAccount createBankAccount(String ownerName, double initialBalance) {
        String newAccountId = generateNewAccountId();
        BankAccount newAccount = new BankAccount(newAccountId, ownerName, initialBalance);
        return bankAccountRepository.save(newAccount);
    }

    /**
     * Method gets all bank accounts
     * @return all bank accounts
     */
    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

    /**
     * Method gets a specific bank account by Id
     * @param accountId
     * @return returns specific bank account
     */
    public Optional<BankAccount> getBankAccountById(String accountId) {
        return bankAccountRepository.findByAccountId(accountId);
    }

    /**
     * Method updates a specific bank account (if not found an empty account is returned)
     * @param accountId
     * @param newBalance
     * @return updated bank account or empty account
     */
    public Optional<BankAccount> updateBankAccountBalance(String accountId, double newBalance) {
        Optional<BankAccount> bankAccountOptional = bankAccountRepository.findByAccountId(accountId);

        if (bankAccountOptional.isPresent()) {
            BankAccount bankAccount = bankAccountOptional.get();
            bankAccount.setBalance(newBalance);
            bankAccountRepository.save(bankAccount);
            return Optional.of(bankAccount);
        }
        return Optional.empty();
    }

    /**
     * Methode implements the soft delete of a bank account
     * @param accountId
     */
    public void deleteBankAccountById(String accountId) {
        Optional<BankAccount> accountOptional = bankAccountRepository.findById(accountId);

        if (accountOptional.isEmpty()) {
            throw new IllegalArgumentException("Bank account with ID " + accountId + " does not exist.");
        }

        BankAccount account = accountOptional.get();
        account.setDeleted(true);
        bankAccountRepository.save(account); // Save changes to the database
    }


    /**
     * Method generates a new bank account id according to our specific requests
     * @return new bank account id
     */
    public String generateNewAccountId() {
        Optional<BankAccount> lastAccount = bankAccountRepository.findTopByOrderByAccountIdDesc();
        int newIdNumber = 1; // Start with 1 if no accounts exist

        if (lastAccount.isPresent()) {
            String lastId = lastAccount.get().getAccountId();
            int lastNumber = Integer.parseInt(lastId.split("-")[1]);
            newIdNumber = lastNumber + 1;
        }
        return String.format("K-%04d", newIdNumber);
    }
}
