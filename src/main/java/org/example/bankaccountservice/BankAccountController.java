package org.example.bankaccountservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    /**
     * Post endpoint to generate a new bank account
     * @param bankAccount
     * @return http status and new bank account
     */
    @PostMapping
    public ResponseEntity<BankAccount> createBankAccount(@RequestBody BankAccount bankAccount) {
        BankAccount newAccount = bankAccountService.createBankAccount(bankAccount.getOwnerName(), bankAccount.getBalance());
        return ResponseEntity.status(HttpStatus.CREATED).body(newAccount);
    }

    /**
     * Get endpoint that returns all bank accounts (including a http not found response)
     * @return http status and all bank accounts
     */
    @GetMapping
    public ResponseEntity<List<BankAccount>> getAllBankAccounts() {
        List<BankAccount> accounts = bankAccountService.getAllBankAccounts();
        if (accounts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 Not Found if not found
        } else {
            return ResponseEntity.ok(accounts); // Return 200 OK if the account is found
        }
    }

    /**
     * Get endpoint that returns a specific bank account (including a http not found response)
     * @param accountId
     * @return
     */
    @GetMapping("/{accountId}")
    public ResponseEntity<BankAccount> getBankAccountById(@PathVariable String accountId) {
        Optional<BankAccount> bankAccount = bankAccountService.getBankAccountById(accountId);
        return bankAccount.map(ResponseEntity::ok) // Return 200 OK if the account is found
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Return 404 Not Found if not found
    }

    /**
     * Get endpoint that updates a specific bank account (including a http not found response)
     * @param accountId
     * @param updatedAccount
     * @return
     */
    @PutMapping("/{accountId}")
    public ResponseEntity<BankAccount> updateBankAccountBalance(
            @PathVariable String accountId,
            @RequestBody BankAccount updatedAccount) {
        Optional<BankAccount> result = bankAccountService.updateBankAccountBalance(accountId, updatedAccount.getBalance());
        return result.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Delete specific bank account (including a http not found response)
     * @param accountId
     * @return
     */
    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteBankAccountById(@PathVariable String accountId) {
        try {
            bankAccountService.deleteBankAccountById(accountId);
            return ResponseEntity.ok().build(); // Return 200 OK
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 Not Found if the account does not exist
        }
    }
}
