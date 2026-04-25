package com.szywoj.simplestock.stock;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stocks")
@Data
@NoArgsConstructor
public class Stock {

    @Id
    private String name;

    @Column(nullable = false)
    private int quantity;

}
