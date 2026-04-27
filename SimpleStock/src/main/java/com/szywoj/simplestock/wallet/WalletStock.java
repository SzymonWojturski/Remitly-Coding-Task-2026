package com.szywoj.simplestock.wallet;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "wallet_stocks")
@Getter
@Setter
@NoArgsConstructor
public class WalletStock {

    @EmbeddedId
    private WalletStockId id;

    @Column(nullable = false)
    private long quantity;

    public WalletStock(String walletId, String stockName, long quantity) {
        this.id = new WalletStockId(walletId, stockName);
        this.quantity = quantity;
    }
}