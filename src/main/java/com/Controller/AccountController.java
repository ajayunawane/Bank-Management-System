package com.Controller;

import com.Entity.Account;
import com.Service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin("*")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {

        this.accountService = accountService;
    }


    // CREATE ACCOUNT
    @PostMapping("/{customerId}")
    public Account createAccount(
            @PathVariable Long customerId,
            @RequestBody Account account) {

        return accountService.createAccount(
                customerId,
                account
        );
    }


    // GET ALL ACCOUNTS
    @GetMapping
    public List<Account> getAllAccounts() {

        return accountService.getAllAccounts();
    }


    // GET ACCOUNT BY ID
    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) {

        return accountService.getAccountById(id);
    }

    // UPDATE ACCOUNT
    @PutMapping("/{id}")
    public Account updateAccount(
            @PathVariable Long id,
            @RequestBody Account account) {

        return accountService.updateAccount(id, account);
    }



    // DELETE ACCOUNT
    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Long id) {

        accountService.deleteAccount(id);

        return "Account deleted successfully";
    }
}