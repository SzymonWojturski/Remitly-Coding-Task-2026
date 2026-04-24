package com.szywoj.simplestock.audit.dto;

import java.util.List;

public record AuditLogListDTO(
        List<AuditLogDTO> log
) {}