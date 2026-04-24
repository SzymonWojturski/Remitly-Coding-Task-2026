package com.szywoj.simplestock.audit;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "audit_log")
@Getter
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
}