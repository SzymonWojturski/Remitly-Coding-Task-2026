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
                .map(a -> AuditLogDTO.builder()
                        .type(a.getType())
                        .walletId(a.getWalletId())
                        .stockName(a.getStockName())
                        .build()
                )
                .toList();

        return new AuditLogListDTO(logs);
    }
}