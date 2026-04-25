package com.szywoj.simplestock.bank;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stocks")
@Getter
@Setter
@NoArgsConstructor
public class Bank {

    @Id
    private String name;

    @Column(nullable = false)
    private int quantity;

}
