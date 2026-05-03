package com.szywoj.simplestock.wallet.dto;

import com.szywoj.simplestock.enums.OperationType;

public record OperationRequest(
        OperationType type
) {}
