package com.szywoj.simplestock.wallet.dto;

import com.szywoj.simplestock.audit.OperationType;

public record OperationRequest(
        OperationType type
) {}
