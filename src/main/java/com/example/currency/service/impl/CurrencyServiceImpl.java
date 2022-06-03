package com.example.currency.service.impl;

import com.example.currency.client.OpenExchangeRatesClient;
import com.example.currency.pojo.ExchangeRates;
import com.example.currency.service.CurrencyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final OpenExchangeRatesClient openExchangeRatesClient;

    @Value("${openexchangerates.app.id}")
    private String appId;

    @Value("${openexchangerates.currency.base}")
    private String base;

    public CurrencyServiceImpl(OpenExchangeRatesClient openExchangeRatesClient) {
        this.openExchangeRatesClient = openExchangeRatesClient;
    }

    @Override
    public Boolean isIncreased(String code) {
        ExchangeRates exchangeRates = openExchangeRatesClient.getLatestRates(appId, base);

        if (!exchangeRates.getRates().containsKey(code)){
            return null;
        }

        ExchangeRates yesterdayRates = openExchangeRatesClient.getHistoricalRates(appId, base, String.valueOf(LocalDate.now().minusDays(1)));

        return yesterdayRates.getRates().get(code) < exchangeRates.getRates().get(code);
    }
}
