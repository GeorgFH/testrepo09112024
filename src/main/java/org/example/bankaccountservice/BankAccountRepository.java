package org.example.bankaccountservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    Optional<BankAccount> findTopByOrderByAccountIdDesc();

    Optional<BankAccount> findByAccountId(String accountId);

}
