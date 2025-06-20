package com.algospire.url.shortner.dto;

import java.time.LocalDateTime;

public record UrlShortenRequest(
        String originalUrl,
        String customAlias
) {}
