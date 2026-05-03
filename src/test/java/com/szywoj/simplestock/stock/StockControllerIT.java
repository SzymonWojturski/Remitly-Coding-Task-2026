package com.szywoj.simplestock.stock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class StockControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldSetAndGetStocks() throws Exception {
        mockMvc.perform(post("/stocks")
                        .contentType("application/json")
                        .content("""
                        {
                          "stocks":[
                            {"name":"apple","quantity":10},
                            {"name":"tesla","quantity":5}
                          ]
                        }
                        """))
                .andExpect(status().isOk());

        mockMvc.perform(get("/stocks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stocks.length()").value(2));
    }

    @Test
    void shouldDecreaseStockAfterBuy() throws Exception {
        mockMvc.perform(post("/stocks")
                        .contentType("application/json")
                        .content("""
                        {
                          "stocks":[
                            {"name":"apple","quantity":10}
                          ]
                        }
                        """))
                .andExpect(status().isOk());

        mockMvc.perform(post("/wallets/w1/stocks/apple")
                        .contentType("application/json")
                        .content("""
                        {"type":"buy"}
                        """))
                .andExpect(status().isOk());

        mockMvc.perform(get("/stocks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stocks[0].name").value("apple"))
                .andExpect(jsonPath("$.stocks[0].quantity").value(9));
    }
}