package com.szywoj.simplestock.wallet;

import com.szywoj.simplestock.wallet.dto.OperationRequest;
import com.szywoj.simplestock.wallet.dto.WalletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WalletController {

    private final WalletService service;

    @PostMapping("/wallets/{walletId}/stocks/{stockName}")
    public void operate(
            @PathVariable String walletId,
            @PathVariable String stockName,
            @RequestBody OperationRequest request
    ) {
        service.operate(walletId, stockName, request.type());
    }

    @GetMapping("/wallets/{walletId}")
    public WalletResponse getWallet(@PathVariable String walletId) {
        return service.getWallet(walletId);
    }

    @GetMapping("/wallets/{walletId}/stocks/{stockName}")
    public long getStock(
            @PathVariable String walletId,
            @PathVariable String stockName
    ) {
        return service.getStockQuantity(walletId, stockName);
    }
}