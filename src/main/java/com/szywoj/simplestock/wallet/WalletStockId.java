package com.szywoj.simplestock.wallet;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletStockId implements Serializable {

    private String walletId;
    private String stockName;
}