package com.glyneria.iwrapper.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import com.glyneria.iwrapper.model.dtos.TransactionRequest;
import com.glyneria.iwrapper.model.entities.User;
import com.glyneria.iwrapper.service.IdempotencyService;
import com.glyneria.iwrapper.service.RequestService;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class APIController {
    private final RequestService requestService;
    private final IdempotencyService idempotencyService;

    @GetMapping
    public ResponseEntity<List<User>> getAccounts() {
        List<User> accounts = requestService.getAccounts();
        return ResponseEntity.ok(accounts);
    }
    
    @PostMapping("/{id}/transact")
    public ResponseEntity<?> transact(
        @PathVariable UUID id,
        @RequestHeader UUID idempotencyKey,
        @RequestBody TransactionRequest transaction) {

        boolean acquired = idempotencyService.acquireLock(idempotencyKey);

        if (!acquired) {
            String currentRecord = idempotencyService.getExistingRecord(idempotencyKey);

            if (currentRecord != null && currentRecord.contains("IN_PROGRESS")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Request is currently being processed.");
            }
        }

        try {
            User request = requestService.transact(id, idempotencyKey, transaction);

            ObjectMapper mapper = new ObjectMapper();
            String jsonResult = mapper.writeValueAsString(request);

            idempotencyService.saveResult(idempotencyKey, jsonResult, true);

            return ResponseEntity.ok(request);
        } catch (Exception e) {
            String errorResult = String.format("{\"error\":\"%s\"}", e.getMessage());
            idempotencyService.saveResult(idempotencyKey, errorResult, false);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }
}
    

