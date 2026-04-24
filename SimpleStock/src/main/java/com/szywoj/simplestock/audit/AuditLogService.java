package com.szywoj.simplestock.audit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository repository;

    public List<AuditLogDto> getLog() {
        return repository.findAllByOrderByIdAsc()
                .stream()
                .map(a -> new AuditLogDto(
                        a.getType(),
                        a.getWalletId(),
                        a.getStockName()
                ))
                .toList();
    }
}