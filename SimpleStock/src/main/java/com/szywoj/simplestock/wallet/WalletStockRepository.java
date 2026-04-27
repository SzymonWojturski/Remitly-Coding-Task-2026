package com.szywoj.simplestock.wallet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletStockRepository extends JpaRepository<WalletStock, WalletStockId> {

    Optional<WalletStock> findByIdWalletIdAndIdStockName(String walletId, String stockName);

    List<WalletStock> findAllByIdWalletId(String walletId);
}

