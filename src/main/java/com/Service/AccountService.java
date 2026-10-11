package com.Service;

import com.Entity.Account;
import com.Entity.Customer;
import com.Repository.AccountRepository;
import com.Repository.CustomerRepository;
import com.Repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository,
                          CustomerRepository customerRepository,
                   TransactionRepository transactionRepository) {

        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    private String generateAccountNumber() {

        java.util.Random random = new java.util.Random();
        String number;

        do {
            number = String.valueOf(
                    1000000000L + random.nextInt(900000000)
            );
        } while (accountRepository.existsByAccountNo(number));

        return number;
    }



    // CREATE ACCOUNT
    public Account createAccount(Long customerId, Account account) {

        // Find customer
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Customer not found with ID: " + customerId
                        ));

        if (account.getAccountNo() == null ||
                account.getAccountNo().isBlank()) {

            account.setAccountNo(generateAccountNumber());
        }


        // Account creation date
        account.setAccountCreateDate(LocalDate.now());

        // Default balance
        if (account.getBalance() == null) {
            account.setBalance(0.0);
        }

        // Account status
        account.setStatus("ACTIVE");

        // Customer ↔ Account relationship
        account.setCustomer(customer);
        customer.setAccount(account);

        // Save account
        Account savedAccount = accountRepository.save(account);

        // Save customer
        customerRepository.save(customer);

        return savedAccount;
    }


    // GET ALL ACCOUNTS
    public List<Account> getAllAccounts() {

        return accountRepository.findAll();
    }


    // GET ACCOUNT BY ID
    public Account getAccountById(Long id) {

        return accountRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));
    }

    // UPDATE ACCOUNT
    public Account updateAccount(Long id, Account updatedAccount) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Account not found with ID: " + id));

        // Update Account Type
        if (updatedAccount.getAccountType() != null &&
                !updatedAccount.getAccountType().isBlank()) {
            account.setAccountType(updatedAccount.getAccountType());
        }

        // Update Branch
        if (updatedAccount.getBranch() != null &&
                !updatedAccount.getBranch().isBlank()) {
            account.setBranch(updatedAccount.getBranch());
        }

        // Update IFSC Code
        if (updatedAccount.getIfscCode() != null &&
                !updatedAccount.getIfscCode().isBlank()) {
            account.setIfscCode(updatedAccount.getIfscCode());
        }

        // Update Status
        if (updatedAccount.getStatus() != null &&
                !updatedAccount.getStatus().isBlank()) {
            account.setStatus(updatedAccount.getStatus());
        }

        return accountRepository.save(account);
    }



    // DELETE ACCOUNT
    public void deleteAccount(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Account not found with ID: " + id));

        boolean hasTransactions =
                transactionRepository.existsByAccount_AccountId(id);

        if (hasTransactions) {
            throw new RuntimeException(
                    "Cannot delete account because transactions exist.");
        }

        Customer customer = account.getCustomer();

        if (customer != null) {
            customer.setAccount(null);
            account.setCustomer(null);
            customerRepository.save(customer);
        }

        accountRepository.delete(account);
    }
}