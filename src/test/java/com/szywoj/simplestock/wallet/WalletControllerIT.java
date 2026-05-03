package com.szywoj.simplestock.wallet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class WalletControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldCreateWalletAndBuyStock() throws Exception {
        mockMvc.perform(post("/stocks")
                .contentType("application/json")
                .content("""
                {"stocks":[{"name":"apple","quantity":10}]}
                """));

        mockMvc.perform(post("/wallets/w1/stocks/apple")
                        .contentType("application/json")
                        .content("""
                        {"type":"buy"}
                        """))
                .andExpect(status().isOk());

        mockMvc.perform(get("/wallets/w1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("w1"))
                .andExpect(jsonPath("$.stocks[0].quantity").value(1));
    }

    @Test
    void shouldReturnStockQuantity() throws Exception {
        mockMvc.perform(post("/stocks")
                .contentType("application/json")
                .content("""
                {"stocks":[{"name":"apple","quantity":10}]}
                """));

        mockMvc.perform(post("/wallets/w1/stocks/apple")
                .contentType("application/json")
                .content("""
                {"type":"buy"}
                """));

        mockMvc.perform(get("/wallets/w1/stocks/apple"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void shouldFailBuyWhenNoStockInBank() throws Exception {
        mockMvc.perform(post("/stocks")
                .contentType("application/json")
                .content("""
                {"stocks":[{"name":"apple","quantity":0}]}
                """));

        mockMvc.perform(post("/wallets/w1/stocks/apple")
                        .contentType("application/json")
                        .content("""
                        {"type":"buy"}
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldFailSellWhenNoStockInWallet() throws Exception {
        mockMvc.perform(post("/stocks")
                .contentType("application/json")
                .content("""
                {"stocks":[{"name":"apple","quantity":10}]}
                """));

        mockMvc.perform(post("/wallets/w1/stocks/apple")
                        .contentType("application/json")
                        .content("""
                        {"type":"sell"}
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldSellStock() throws Exception {
        mockMvc.perform(post("/stocks")
                .contentType("application/json")
                .content("""
                {"stocks":[{"name":"apple","quantity":10}]}
                """));

        mockMvc.perform(post("/wallets/w1/stocks/apple")
                .contentType("application/json")
                .content("""
                {"type":"buy"}
                """));

        mockMvc.perform(post("/wallets/w1/stocks/apple")
                        .contentType("application/json")
                        .content("""
                        {"type":"sell"}
                        """))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn404ForUnknownStock() throws Exception {
        mockMvc.perform(post("/stocks")
                .contentType("application/json")
                .content("""
                {"stocks":[{"name":"apple","quantity":10}]}
                """));

        mockMvc.perform(post("/wallets/w1/stocks/unknown")
                        .contentType("application/json")
                        .content("""
                        {"type":"buy"}
                        """))
                .andExpect(status().isNotFound());
    }
}