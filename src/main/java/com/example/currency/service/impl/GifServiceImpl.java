package com.example.currency.service.impl;

import com.example.currency.client.GiphyClient;
import com.example.currency.service.CurrencyService;
import com.example.currency.service.GifService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GifServiceImpl implements GifService {

    private final CurrencyService currencyService;

    private final GiphyClient giphyClient;

    @Value("${giphy.api.key}")
    private String giphyKey;

    @Value("${giphy.currency.increased}")
    private String increasedTag;

    @Value("${giphy.currency.decreased}")
    private String decreasedTag;


    public GifServiceImpl(CurrencyService currencyService, GiphyClient giphyClient) {
        this.currencyService = currencyService;
        this.giphyClient = giphyClient;
    }

    @Override
    public Object getGif(String code) {

        Boolean currencyIncreased = currencyService.isIncreased(code.toUpperCase());

        if (currencyIncreased == null) {
            return null;
        }

        return currencyIncreased ? giphyClient.getRandomGif(giphyKey, increasedTag).getBody()
                : giphyClient.getRandomGif(giphyKey, decreasedTag).getBody();
    }
}
