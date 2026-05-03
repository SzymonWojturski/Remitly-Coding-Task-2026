package com.szywoj.simplestock.audit;

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
class AuditControllerIT {
    @Test
    void debugConfig() {
        System.out.println("URL: " + System.getProperty("spring.datasource.url"));
    }
    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldLogOnlySuccessfulOperations() throws Exception {
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
                """));

        mockMvc.perform(post("/wallets/w1/stocks/apple")
                .contentType("application/json")
                .content("""
                {"type":"sell"}
                """));

        mockMvc.perform(get("/log"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.log.length()").value(2));
    }
}
