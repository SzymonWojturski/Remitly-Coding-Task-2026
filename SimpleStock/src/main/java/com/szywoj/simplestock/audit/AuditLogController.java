package com.szywoj.simplestock.audit;

import com.szywoj.simplestock.audit.dto.AuditLogListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService service;

    @GetMapping("/log")
    public AuditLogListDTO getLog() {
        return service.getLog();
    }
}