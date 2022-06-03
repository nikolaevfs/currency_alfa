package com.example.currency.client;

import com.example.currency.pojo.ExchangeRates;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "openExchangeRatesClient", url = "${openexchangerates.url}")
public interface OpenExchangeRatesClient {

    @GetMapping("/latest.json")
    ExchangeRates getLatestRates(
            @RequestParam("app_id") String appId,
            @RequestParam("base") String base
    );

    @GetMapping("/historical/{date}.json")
    ExchangeRates getHistoricalRates(
            @RequestParam("app_id") String appId,
            @RequestParam("base") String base,
            @PathVariable String date
    );
}
