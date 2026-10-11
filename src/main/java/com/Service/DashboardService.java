package com.Service;


import com.Repository.AccountRepository;
import com.Repository.CustomerRepository;
import com.Repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public DashboardService(
            CustomerRepository customerRepository,
            AccountRepository accountRepository,
            TransactionRepository transactionRepository) {

        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Map<String, Object> getDashboardData() {

        Map<String, Object> data = new HashMap<>();

        long totalCustomers =
                customerRepository.count();

        long totalAccounts =
                accountRepository.count();

        long totalTransactions =
                transactionRepository.count();

        double totalBalance =
                accountRepository.findAll()
                        .stream()
                        .mapToDouble(account ->
                                account.getBalance() == null
                                        ? 0.0
                                        : account.getBalance())
                        .sum();

        data.put("totalCustomers", totalCustomers);
        data.put("totalAccounts", totalAccounts);
        data.put("totalTransactions", totalTransactions);
        data.put("totalBalance", totalBalance);

        return data;
    }
}
