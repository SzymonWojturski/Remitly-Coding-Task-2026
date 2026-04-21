package com.szywoj.simplestock.bank;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankController {
    @GetMapping("/")
    public String hello(){
        return "Hello";
    }
}
