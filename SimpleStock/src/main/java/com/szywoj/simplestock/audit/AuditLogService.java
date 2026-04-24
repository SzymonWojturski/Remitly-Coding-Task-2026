package com.szywoj.simplestock.audit;

import com.szywoj.simplestock.audit.dto.AuditLogDTO;
import com.szywoj.simplestock.audit.dto.AuditLogListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository repository;

    public AuditLogListDTO getLog() {

        List<AuditLogDTO> logs = repository.findAllByOrderByCreatedAtAsc()
                .stream()
                .map(this::buildAuditLogDTO)
                .toList();

        return new AuditLogListDTO(logs);
    }

    private AuditLogDTO buildAuditLogDTO(AuditLog auditLog) {
        return AuditLogDTO.builder()
                .type(auditLog.getType())
                .walletId(auditLog.getWalletId())
                .stockName(auditLog.getStockName())
                .build();
    }
}