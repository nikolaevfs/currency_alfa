package com.example.currency.pojo;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ExchangeRates {
    String disclaimer;
    String license;
    Integer timestamp;
    String base;
    private Map<String, Double> rates;
}
