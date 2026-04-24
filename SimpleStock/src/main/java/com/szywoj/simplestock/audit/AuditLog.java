package com.szywoj.simplestock.audit;

import jakarta.persistence.*;

@Entity
@Table(name = "audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    @Column(name = "wallet_id")
    private String walletId;

    @Column(name = "stock_name")
    private String stockName;

    public AuditLog() {}

    public AuditLog(String type, String walletId, String stockName) {
        this.type = type;
        this.walletId = walletId;
        this.stockName = stockName;
    }

    public Long getId() { return id; }
    public String getType() { return type; }
    public String getWalletId() { return walletId; }
    public String getStockName() { return stockName; }
}