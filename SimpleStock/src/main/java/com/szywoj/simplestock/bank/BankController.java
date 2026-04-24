package com.szywoj.simplestock.bank;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankController {
    @GetMapping("/stocks")
    public String getStocks() {
        throw new UnsupportedOperationException("Endpoint not implemented");
    }

    @PostMapping("/stocks")
    public String setStocks() {
        throw new UnsupportedOperationException("Endpoint not implemented");
    }
}
