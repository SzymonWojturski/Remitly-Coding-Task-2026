package com.szywoj.simplestock.audit.dto;

import java.util.List;

public record AuditLogListResponse(
        List<AuditLogDTO> log
) {}