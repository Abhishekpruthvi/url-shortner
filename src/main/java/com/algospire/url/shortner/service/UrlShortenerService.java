package com.algospire.url.shortner.service;

import com.algospire.url.shortner.dto.UrlShortenRequest;
import com.algospire.url.shortner.dto.UrlShortenResponse;
import com.algospire.url.shortner.entity.UrlMapping;
import com.algospire.url.shortner.exception.AliasAlreadyExistsException;
import com.algospire.url.shortner.exception.UrlExpiredException;
import com.algospire.url.shortner.exception.UrlNotFoundException;
import com.algospire.url.shortner.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UrlShortenerService {

    private final UrlMappingRepository repository;
    private final String baseUrl;

    public UrlShortenerService(UrlMappingRepository repository,
                               @Value("${app.base-url}") String baseUrl) {
        this.repository = repository;
        this.baseUrl = baseUrl;
    }

    public UrlShortenResponse createShortUrl(UrlShortenRequest request) {

        String shortCode = (request.customAlias() != null && !request.customAlias().isBlank())
                ? request.customAlias()
                : UUID.randomUUID().toString().replace("-", "").substring(0, 7);

        if (repository.existsByShortCode(shortCode)) {
            throw new AliasAlreadyExistsException("Alias already exists: " + shortCode);
        }

        UrlMapping mapping = new UrlMapping();
        mapping.setOriginalUrl(request.originalUrl());
        mapping.setShortCode(shortCode);
        mapping.setExpiryAt(LocalDateTime.now().plusMinutes(5));
        mapping =  repository.save(mapping);

        return new UrlShortenResponse(baseUrl+shortCode, mapping.getOriginalUrl(), mapping.getExpiryAt());
    }

    public String resolveUrl(String shortCode) {
        UrlMapping mapping = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException("Short code not found: " + shortCode));

        if (mapping.getExpiryAt() != null && LocalDateTime.now().isAfter(mapping.getExpiryAt())) {
            throw new UrlExpiredException("URL expired");
        }

        mapping.setClickCount(mapping.getClickCount() + 1);
        repository.save(mapping);
        return mapping.getOriginalUrl();
    }
}
