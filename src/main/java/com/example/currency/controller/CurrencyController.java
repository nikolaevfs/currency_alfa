package com.example.currency.controller;

import com.example.currency.service.GifService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/currency")
public class CurrencyController {

    private final GifService gifService;

    public CurrencyController(GifService gifService) {
        this.gifService = gifService;
    }

    @GetMapping("/gif/{code}")
    public ResponseEntity<Object> getChangeGif(@PathVariable String code) {
        Object gifResponse = gifService.getGif(code);

        if (gifResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no currency with code = " + code);
        }

        return ResponseEntity.ok(gifResponse);
    }
}
