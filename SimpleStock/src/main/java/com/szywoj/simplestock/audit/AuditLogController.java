package com.szywoj.simplestock.audit;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService service;

    @GetMapping("/log")
    public Map<String, List<AuditLogDto>> getLog() {
        return Map.of("log", service.getLog());
    }
}