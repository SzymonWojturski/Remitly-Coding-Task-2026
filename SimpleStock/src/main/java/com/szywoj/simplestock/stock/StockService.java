package com.szywoj.simplestock.stock;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankService {
    private final BankRepository repository;

    @Transactional
    public void replaceAll(List<> newStocks) {
        repository.deleteAll();
        repository.saveAll(newStocks);
    }
}
