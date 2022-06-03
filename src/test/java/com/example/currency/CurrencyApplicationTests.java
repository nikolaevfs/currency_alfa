package com.example.currency;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CurrencyApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void correctCurrency() throws Exception {
        this.mockMvc.perform(get("/api/currency/gif/RUB"))
                .andExpect(status().isOk());
    }

    @Test
    public void incorrectCurrency() throws Exception {
        this.mockMvc.perform(get("/api/currency/gif/RUB"))
                .andExpect(status().isOk());
    }
}
