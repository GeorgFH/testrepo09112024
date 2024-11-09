package org.example.bankaccountservice;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity

public class BankAccount {

    @Id
    private String accountId;

    private String ownerName;

    private double balance;

    private boolean isDeleted;

    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;


    public BankAccount() {}

    public BankAccount(String accountId, String ownerName, double balance) {
        this.accountId = accountId;
        this.ownerName = ownerName;
        this.balance = balance;
        this.isDeleted = false;
        this.createdAt = LocalDateTime.now();
    }

    // Getter und Setter
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
        this.deletedAt = deleted ? LocalDateTime.now() : null;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }
}
