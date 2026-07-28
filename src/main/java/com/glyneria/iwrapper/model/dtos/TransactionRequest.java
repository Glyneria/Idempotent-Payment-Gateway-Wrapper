package com.glyneria.iwrapper.model.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionRequest(
    @NotNull(message = "Account ID is required")
    UUID accountId,

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    BigDecimal amount,

    @NotNull(message = "Type is required (DEBIT OR CREDIT)")
    String type
) {}
