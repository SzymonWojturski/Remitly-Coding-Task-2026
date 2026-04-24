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
        List<AuditLogDTO> logs = repository.findAllByOrderByIdAsc()
        .stream()
        .map(a -> new AuditLogDTO(
                a.getType(),
                a.getWalletId(),
                a.getStockName()
        ))
        .toList();

        return new AuditLogListDTO(logs);
    }
}