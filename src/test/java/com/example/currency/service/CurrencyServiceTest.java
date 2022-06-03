package com.example.currency.service;

import com.example.currency.client.OpenExchangeRatesClient;
import com.example.currency.pojo.ExchangeRates;
import com.example.currency.service.impl.CurrencyServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@PropertySource("classpath:application.properties")
public class CurrencyServiceTest {

    @Mock
    private OpenExchangeRatesClient openExchangeRatesClient;

    @Value("${openexchangerates.app.id}")
    private String appId;

    @Value("${openexchangerates.currency.base}")
    private String base;

    CurrencyService currencyService;


    @BeforeEach
    public void init() {
        currencyService = new CurrencyServiceImpl(openExchangeRatesClient);
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(currencyService)
                .build();

        ReflectionTestUtils.setField(currencyService, "appId", appId);
        ReflectionTestUtils.setField(currencyService, "base", base);
    }

    @Test
    public void currencyDecreased() {
        //given
        String code = "RUB";

        ExchangeRates todayRates = ExchangeRates.builder().rates(Map.of(code, 50.4)).build();
        ExchangeRates yesterdayRates = ExchangeRates.builder().rates(Map.of(code, 48.43)).build();

        //when
        Mockito.when(openExchangeRatesClient.getLatestRates(appId, base)).thenReturn(todayRates);
        Mockito.when(openExchangeRatesClient.getHistoricalRates(appId, base, String.valueOf(LocalDate.now().minusDays(1)))).thenReturn(yesterdayRates);

        //then
        Assertions.assertTrue(currencyService.isIncreased(code));
    }

    @Test
    public void currencyIncreased() {
        //given
        ExchangeRates todayRates = ExchangeRates.builder().rates(Map.of("RUB", 50.4)).build();
        ExchangeRates yesterdayRates = ExchangeRates.builder().rates(Map.of("RUB", 52.43)).build();

        //when
        Mockito.when(openExchangeRatesClient.getLatestRates(appId, base)).thenReturn(todayRates);
        Mockito.when(openExchangeRatesClient.getHistoricalRates(appId, base, String.valueOf(LocalDate.now().minusDays(1)))).thenReturn(yesterdayRates);

        //then
        Assertions.assertFalse(currencyService.isIncreased("RUB"));
    }

    @Test
    public void currencyTheSame() {
        //given
        ExchangeRates todayRates = ExchangeRates.builder().rates(Map.of("RUB", 50.4)).build();
        ExchangeRates yesterdayRates = ExchangeRates.builder().rates(Map.of("RUB", 52.43)).build();

        //when
        Mockito.when(openExchangeRatesClient.getLatestRates(appId, base)).thenReturn(todayRates);
        Mockito.when(openExchangeRatesClient.getHistoricalRates(appId, base, String.valueOf(LocalDate.now().minusDays(1)))).thenReturn(yesterdayRates);

        //then
        Assertions.assertFalse(currencyService.isIncreased("RUB"));
    }

    @Test
    public void currencyNotExists() {
        //given
        ExchangeRates todayRates = ExchangeRates.builder().rates(Map.of("RUB", 50.4)).build();

        //when
        Mockito.when(openExchangeRatesClient.getLatestRates(appId, base)).thenReturn(todayRates);

        //then
        Assertions.assertNull(currencyService.isIncreased("Not_Existing"));
        verify(openExchangeRatesClient, never()).getHistoricalRates(any(), any(), any());
    }
}
