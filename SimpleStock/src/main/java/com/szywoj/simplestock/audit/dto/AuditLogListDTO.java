package com.szywoj.simplestock.audit.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
public record AuditLogListDTO(
        List<AuditLogDTO> log
) {
}