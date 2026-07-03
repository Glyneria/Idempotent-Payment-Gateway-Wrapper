package com.project.Iwrapper.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.project.Iwrapper.model.AccountUser;
import com.project.Iwrapper.repository.AccountRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountRepository repo;

    public AccountController(AccountRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<AccountUser> getAllAccountUsers() {
        return (List<AccountUser>) repo.findAll();
    }
    
    @GetMapping("/{id}")
    public AccountUser getAccountById(@PathVariable Long id) {
        return repo.findById(id.longValue());
    }
    
    @GetMapping("/search")
    public List<AccountUser> getAccountNumberByName(@RequestParam String ownerName) {
        return repo.findByAccountNumber(ownerName);
    }

    @PostMapping
    public AccountUser createAccount(@RequestBody AccountUser newAccount) {
        return repo.save(newAccount);
    }

    @PutMapping("/{id}/deposit")
    public AccountUser deposit(@PathVariable Long id, @RequestParam double amount) {
        AccountUser account = repo.findById(id.longValue());

        if (account != null) {
            double currentBalance = account.getBalance();

            double updatedBalance = currentBalance + amount;

            account.setBalance(updatedBalance);

            return repo.save(account);
        }
        return null;
    }

    @PutMapping("/{id}/withdraw")
    public AccountUser withdraw(@PathVariable Long id, @RequestParam double amount) {
        AccountUser account = repo.findById(id.longValue());

        if (account != null) {
            double currentBalance = account.getBalance();

            double updatedBalance = currentBalance - amount;

            account.setBalance(updatedBalance);

            return repo.save(account);
        }
        return null;
    }
    
    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
    

