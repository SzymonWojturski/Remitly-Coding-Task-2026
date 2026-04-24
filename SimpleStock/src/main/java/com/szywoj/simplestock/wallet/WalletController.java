package com.szywoj.simplestock.wallet;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {

    @PostMapping("/wallets/{wallet_id}/stocks/{stock_name}")
    public String buyAndSell(
            @PathVariable("wallet_id") String walletId,
            @PathVariable("stock_name") String stockName
    ) {
        throw new UnsupportedOperationException("Endpoint not implemented");
    }

    @GetMapping("/wallets/{wallet_id}/stocks/{stock_name}")
    public String walletStockState(
            @PathVariable("wallet_id") String walletId,
            @PathVariable("stock_name") String stockName
    ) {
        throw new UnsupportedOperationException("Endpoint not implemented");
    }

    @GetMapping("/wallets/{wallet_id}")
    public String walletState(
            @PathVariable("wallet_id") String walletId
            ) {
        throw new UnsupportedOperationException("Endpoint not implemented");
    }
}