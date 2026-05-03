package com.szywoj.simplestock.wallet;

import com.szywoj.simplestock.audit.AuditLog;
import com.szywoj.simplestock.audit.AuditLogRepository;
import com.szywoj.simplestock.enums.OperationType;
import com.szywoj.simplestock.stock.Stock;
import com.szywoj.simplestock.stock.StockRepository;
import com.szywoj.simplestock.wallet.dto.WalletResponse;
import com.szywoj.simplestock.wallet.dto.WalletStockDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
        walletRepository.findById(walletId)
                .orElseGet(() -> walletRepository.save(new Wallet(walletId)));

        if (type == OperationType.BUY) {
            buy(walletId, stockName);
        } else {
            sell(walletId, stockName);
        }
    }

    private void buy(String walletId, String stockName) {
            Stock stock = stockRepository.findByNameWithLock(stockName)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            if (stock.getQuantity() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            WalletStock ws = walletStockRepository
                    .findByIdWalletIdAndIdStockName(walletId, stockName)
                    .orElse(new WalletStock(walletId, stockName, 0));

            stock.setQuantity(stock.getQuantity() - 1);
            ws.setQuantity(ws.getQuantity() + 1);

            stockRepository.save(stock);
            walletStockRepository.save(ws);

            auditLogRepository.save(new AuditLog(OperationType.BUY, walletId, stockName));
        }

        private void sell (String walletId, String stockName){
            WalletStock ws = walletStockRepository
                    .findByIdWalletIdAndIdStockName(walletId, stockName)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

            if (ws.getQuantity() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            Stock stock = stockRepository.findById(stockName)
                    .orElse(null);

            if (stock == null) {
                stock = new Stock();
                stock.setName(stockName);
                stock.setQuantity(0);
            }

            ws.setQuantity(ws.getQuantity() - 1);
            stock.setQuantity(stock.getQuantity() + 1);

            if (ws.getQuantity() == 0) {
                walletStockRepository.delete(ws);
            } else {
                walletStockRepository.save(ws);
            }

            stockRepository.save(stock);

            auditLogRepository.save(new AuditLog(OperationType.SELL, walletId, stockName));
        }

        public WalletResponse getWallet (String walletId){
            walletRepository.findById(walletId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            List<WalletStockDTO> stocks = walletStockRepository
                    .findAllByIdWalletId(walletId)
                    .stream()
                    .map(ws -> new WalletStockDTO(ws.getId().getStockName(), ws.getQuantity()))
                    .toList();

            return new WalletResponse(walletId, stocks);
        }

        public long getStockQuantity (String walletId, String stockName){
            walletRepository.findById(walletId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            stockRepository.findById(stockName)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            return walletStockRepository
                    .findByIdWalletIdAndIdStockName(walletId, stockName)
                    .map(WalletStock::getQuantity)
                    .orElse(0L);
        }
    }