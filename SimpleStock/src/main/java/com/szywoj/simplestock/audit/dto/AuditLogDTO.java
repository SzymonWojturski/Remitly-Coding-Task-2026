package com.szywoj.simplestock.audit.dto;

import lombok.Builder;

@Builder
public record AuditLogDTO(
        String type,
        String walletId,
        String stockName
){}