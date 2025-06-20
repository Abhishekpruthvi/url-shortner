package com.algospire.url.shortner.dto;

import java.time.LocalDateTime;

public record UrlShortenResponse(
        String shortUrl,
        String originalUrl,
        LocalDateTime expiryAt
) {}
