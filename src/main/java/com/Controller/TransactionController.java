package com.Controller;

import com.Entity.Transaction;
import com.Service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin("*")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    // DEPOSIT
    @PostMapping("/deposit")
    public Transaction deposit(
            @RequestParam String accountNo,
            @RequestParam Double amount) {

        return transactionService.deposit(accountNo, amount);
    }
    // WITHDRAW
    @PostMapping("/withdraw")
    public Transaction withdraw(
            @RequestParam String accountNo,
            @RequestParam Double amount) {

        return transactionService.withdraw(accountNo, amount);
    }
    // TRANSACTION HISTORY
    @GetMapping("/{accountNo}")
    public List<Transaction> getTransactions(
            @PathVariable String accountNo) {

        return transactionService.getTransactions(accountNo);
    }
    // FUND TRANSFER
    @PostMapping("/transfer")
    public Transaction transfer(
            @RequestParam String fromAccountNo,
            @RequestParam String toAccountNo,
            @RequestParam Double amount) {

        return transactionService.transfer(
                fromAccountNo,
                toAccountNo,
                amount);
    }

}
