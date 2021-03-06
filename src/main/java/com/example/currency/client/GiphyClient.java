package com.example.currency.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "giphyClient", url = "${giphy.url}")
public interface GiphyClient {
    @GetMapping("/random")
    ResponseEntity<Object> getRandomGif(
            @RequestParam("api_key") String apiKey,
            @RequestParam("tag") String tag
    );
}
