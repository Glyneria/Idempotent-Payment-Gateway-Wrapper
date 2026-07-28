package com.glyneria.iwrapper.service;

import com.glyneria.iwrapper.model.dtos.TransactionRequest;
import com.glyneria.iwrapper.model.entities.User;
import com.glyneria.iwrapper.model.enums.TransactionType;
import com.glyneria.iwrapper.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final AccountRepository accountRepository;

    public List<User> getAccounts() {
        return accountRepository.findAll();
    }

    @Transactional
    public User transact(UUID id, UUID idempotencyKey, TransactionRequest request) {
        User account = accountRepository
            .findByIdWithLock(request.accountId())
            .orElseThrow(() -> new RuntimeException("Account not found"));

            TransactionType type = TransactionType.valueOf(request.type().toUpperCase());

            switch (type) {
                case DEBIT -> {
                    if (account.getBalance().compareTo)
                }
            }
        return accountRepository.save(account);
    }
}