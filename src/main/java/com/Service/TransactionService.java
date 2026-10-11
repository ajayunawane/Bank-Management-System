
package com.Service;

import com.Entity.Account;
import com.Entity.Transaction;
import com.Repository.AccountRepository;
import com.Repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    // DEPOSIT
    public Transaction deposit(String accountNo, Double amount) {

        Account account = accountRepository.findAll()
                .stream()
                .filter(a -> accountNo.equals(a.getAccountNo()))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        if (amount == null || amount <= 0) {
            throw new RuntimeException(
                    "Amount must be greater than 0");
        }

        Double newBalance = account.getBalance() + amount;

        account.setBalance(newBalance);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setTransactionType("DEPOSIT");
        transaction.setAmount(amount);
        transaction.setBalanceAfterTransaction(newBalance);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setDescription("Money Deposited");
        transaction.setAccount(account);

        return transactionRepository.save(transaction);
    }

    // WITHDRAW
    public Transaction withdraw(String accountNo, Double amount) {

        Account account = accountRepository.findAll()
                .stream()
                .filter(a -> accountNo.equals(a.getAccountNo()))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        if (amount == null || amount <= 0) {
            throw new RuntimeException(
                    "Amount must be greater than 0");
        }

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient Balance");
        }

        Double newBalance = account.getBalance() - amount;

        account.setBalance(newBalance);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setTransactionType("WITHDRAW");
        transaction.setAmount(amount);
        transaction.setBalanceAfterTransaction(newBalance);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setDescription("Money Withdrawn");
        transaction.setAccount(account);

        return transactionRepository.save(transaction);
    }

    // TRANSACTION HISTORY
    public List<Transaction> getTransactions(String accountNo) {

        Account account = accountRepository.findAll()
                .stream()
                .filter(a -> accountNo.equals(a.getAccountNo()))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "Account not found: " + accountNo));

        return transactionRepository.findAll()
                .stream()
                // FIX: Skip transactions having null account
                .filter(t -> t.getAccount() != null)
                .filter(t -> t.getAccount().getAccountId()
                        .equals(account.getAccountId()))
                .toList();
    }

    // FUND TRANSFER
    public Transaction transfer(
            String fromAccountNo,
            String toAccountNo,
            Double amount) {

        Account fromAccount = accountRepository.findAll()
                .stream()
                .filter(a -> fromAccountNo.equals(a.getAccountNo()))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "Sender account not found"));

        Account toAccount = accountRepository.findAll()
                .stream()
                .filter(a -> toAccountNo.equals(a.getAccountNo()))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "Receiver account not found"));

        if (amount == null || amount <= 0) {
            throw new RuntimeException(
                    "Amount must be greater than 0");
        }

        if (fromAccountNo.equals(toAccountNo)) {
            throw new RuntimeException(
                    "Sender and receiver account cannot be same");
        }

        if (fromAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient Balance");
        }

        // Sender balance
        Double senderNewBalance =
                fromAccount.getBalance() - amount;

        // Receiver balance
        Double receiverNewBalance =
                toAccount.getBalance() + amount;

        fromAccount.setBalance(senderNewBalance);
        toAccount.setBalance(receiverNewBalance);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        // Sender transaction
        Transaction senderTransaction = new Transaction();
        senderTransaction.setTransactionType("TRANSFER");
        senderTransaction.setAmount(amount);
        senderTransaction.setBalanceAfterTransaction(
                senderNewBalance);
        senderTransaction.setTransactionDate(LocalDateTime.now());
        senderTransaction.setDescription(
                "Transferred to account " + toAccountNo);
        senderTransaction.setAccount(fromAccount);

        transactionRepository.save(senderTransaction);

        // Receiver transaction
        Transaction receiverTransaction = new Transaction();
        receiverTransaction.setTransactionType("TRANSFER");
        receiverTransaction.setAmount(amount);
        receiverTransaction.setBalanceAfterTransaction(
                receiverNewBalance);
        receiverTransaction.setTransactionDate(LocalDateTime.now());
        receiverTransaction.setDescription(
                "Received from account " + fromAccountNo);
        receiverTransaction.setAccount(toAccount);

        transactionRepository.save(receiverTransaction);

        return senderTransaction;
    }
}
