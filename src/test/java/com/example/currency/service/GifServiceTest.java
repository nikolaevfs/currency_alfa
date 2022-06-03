package com.example.currency.service;

import com.example.currency.client.GiphyClient;
import com.example.currency.service.impl.GifServiceImpl;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@PropertySource("classpath:application.properties")
public class GifServiceTest {

    @Mock
    private CurrencyService currencyService;

    @Mock
    private GiphyClient giphyClient;

    @Value("${giphy.api.key}")
    private String giphyKey;

    @Value("${giphy.currency.increased}")
    private String increasedTag;

    @Value("${giphy.currency.decreased}")
    private String decreasedTag;

    GifService gifService;


    @BeforeEach
    public void init() {
        gifService = new GifServiceImpl(currencyService, giphyClient);
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(currencyService)
                .build();

        ReflectionTestUtils.setField(gifService, "giphyKey", giphyKey);
        ReflectionTestUtils.setField(gifService, "increasedTag", increasedTag);
        ReflectionTestUtils.setField(gifService, "decreasedTag", decreasedTag);
    }

    @Test
    public void currencyNotExist() {
        //given
        String code = "Not_Existing";

        //when
        Mockito.when(currencyService.isIncreased(code.toUpperCase())).thenReturn(null);

        //then
        Assertions.assertNull(gifService.getGif(code));
        verify(giphyClient, never()).getRandomGif(any(), any());
    }

    @Test
    public void increasedGif() {
        //given
        String code = "RUB";
        ResponseEntity<Object> gif = new ResponseEntity<>(HttpStatus.OK);

        //when
        Mockito.when(currencyService.isIncreased(code.toUpperCase())).thenReturn(true);
        Mockito.when(giphyClient.getRandomGif(giphyKey, increasedTag)).thenReturn(gif);

        //then
        Assertions.assertEquals(gif.getBody(), gifService.getGif(code));
        verify(giphyClient).getRandomGif(giphyKey, increasedTag);
        verify(giphyClient, never()).getRandomGif(giphyKey, decreasedTag);
    }

    @Test
    public void decreasedGif() {
        //given
        String code = "RUB";
        ResponseEntity<Object> gif = new ResponseEntity<>(HttpStatus.OK);

        //when
        Mockito.when(currencyService.isIncreased(code.toUpperCase())).thenReturn(false);
        Mockito.when(giphyClient.getRandomGif(giphyKey, decreasedTag)).thenReturn(gif);

        //then
        Assertions.assertEquals(gif.getBody(), gifService.getGif(code));
        verify(giphyClient,never()).getRandomGif(giphyKey, increasedTag);
        verify(giphyClient).getRandomGif(giphyKey, decreasedTag);
    }
}