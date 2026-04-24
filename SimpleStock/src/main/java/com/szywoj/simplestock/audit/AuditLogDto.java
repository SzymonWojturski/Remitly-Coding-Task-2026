package com.szywoj.simplestock.audit;

public record AuditLogDto(
        String type,
        String wallet_id,
        String stock_name
) {
}
