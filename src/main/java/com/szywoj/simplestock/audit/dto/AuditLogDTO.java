package com.szywoj.simplestock.audit.dto;

import com.szywoj.simplestock.enums.OperationType;
import lombok.Builder;

@Builder
public record AuditLogDTO(
        OperationType type,
        String walletId,
        String stockName
){}