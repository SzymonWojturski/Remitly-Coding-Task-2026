package com.szywoj.simplestock.audit;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;

@Entity
@Table(name = "audit_log")
@Getter
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private Instant createdAt;

    private String type;

    @Column(name = "wallet_id")
    private String walletId;

    @Column(name = "stock_name")
    private String stockName;

    public AuditLog() {
    }

    public AuditLog(String type, String walletId, String stockName) {
        this.type = type;
        this.walletId = walletId;
        this.stockName = stockName;
    }
}