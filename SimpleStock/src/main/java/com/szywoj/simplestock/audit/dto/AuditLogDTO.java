package com.szywoj.simplestock.audit.dto;

import lombok.Builder;
import lombok.Value;

@Builder
public record AuditLogDTO(
        String type,
        String wallet_id,
        String stock_name
){}