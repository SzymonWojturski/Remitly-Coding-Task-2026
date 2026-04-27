package com.szywoj.simplestock.wallet;

import com.szywoj.simplestock.audit.AuditLog;
import com.szywoj.simplestock.audit.AuditLogRepository;
import com.szywoj.simplestock.audit.OperationType;
import com.szywoj.simplestock.stock.Stock;
import com.szywoj.simplestock.stock.StockRepository;
import com.szywoj.simplestock.wallet.dto.WalletResponse;
import com.szywoj.simplestock.wallet.dto.WalletStockDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletStockRepository walletStockRepository;
    private final StockRepository stockRepository;
    private final AuditLogRepository auditLogRepository;

    @Transactional
    public void operate(String walletId, String stockName, OperationType type) {

        Wallet wallet = walletRepository.findById(walletId)
                .orElseGet(() -> walletRepository.save(new Wallet(walletId)));

        if (type == OperationType.BUY) {

            Stock stock = stockRepository.findById(stockName)
                    .orElseThrow(() -> new RuntimeException("stock not found"));

            if (stock.getQuantity() <= 0) {
                throw new RuntimeException("no stock in bank");
            }

            WalletStock ws = walletStockRepository
                    .findByIdWalletIdAndIdStockName(walletId, stockName)
                    .orElse(new WalletStock(walletId, stockName, 0));

            stock.setQuantity(stock.getQuantity() - 1);
            ws.setQuantity(ws.getQuantity() + 1);

            stockRepository.save(stock);
            walletStockRepository.save(ws);

            auditLogRepository.save(new AuditLog(type, walletId, stockName));
        }

        if (type == OperationType.SELL) {

            WalletStock ws = walletStockRepository
                    .findByIdWalletIdAndIdStockName(walletId, stockName)
                    .orElseThrow(() -> new RuntimeException("no stock in wallet"));

            if (ws.getQuantity() <= 0) {
                throw new RuntimeException("no stock in wallet");
            }

            Stock stock = stockRepository.findById(stockName)
                    .orElseThrow(() -> new RuntimeException("stock not found"));

            ws.setQuantity(ws.getQuantity() - 1);
            stock.setQuantity(stock.getQuantity() + 1);

            walletStockRepository.save(ws);
            stockRepository.save(stock);

            auditLogRepository.save(new AuditLog(type, walletId, stockName));
        }
    }

    public WalletResponse getWallet(String walletId) {

        walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("wallet not found"));

        List<WalletStockDTO> stocks = walletStockRepository
                .findAllByIdWalletId(walletId)
                .stream()
                .map(ws -> new WalletStockDTO(
                        ws.getId().getStockName(),
                        ws.getQuantity()
                ))
                .toList();

        return new WalletResponse(walletId, stocks);
    }

    public long getStockQuantity(String walletId, String stockName) {

        return walletStockRepository
                .findByIdWalletIdAndIdStockName(walletId, stockName)
                .map(WalletStock::getQuantity)
                .orElse(0L);
    }
}