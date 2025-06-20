package com.algospire.url.shortner.controller;

import com.algospire.url.shortner.dto.UrlShortenRequest;
import com.algospire.url.shortner.dto.UrlShortenResponse;
import com.algospire.url.shortner.service.UrlShortenerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api")
@Tag(name = "URL Shortener API")
public class UrlShortenerController {

    private final UrlShortenerService service;

    public UrlShortenerController(UrlShortenerService service) {
        this.service = service;
    }

    @Operation(summary = "Create short URL")
    @PostMapping("/shorten")
    public ResponseEntity<UrlShortenResponse> shorten(@RequestBody UrlShortenRequest request) {
        return ResponseEntity.ok(service.createShortUrl(request));
    }

    @Operation(summary = "Redirect to original URL")
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = service.resolveUrl(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
