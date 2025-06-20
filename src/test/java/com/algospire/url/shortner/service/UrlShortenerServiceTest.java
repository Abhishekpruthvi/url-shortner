package com.algospire.url.shortner.service;

import com.algospire.url.shortner.dto.UrlShortenRequest;
import com.algospire.url.shortner.dto.UrlShortenResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UrlShortenerServiceTest {

    @Autowired
    private UrlShortenerService service;

    @Test
    void shouldCreateAndResolveUrl() {
        UrlShortenRequest req = new UrlShortenRequest("https://openai.com", null, null);
        UrlShortenResponse resp = service.createShortUrl(req);

        String code = resp.shortUrl().substring(resp.shortUrl().lastIndexOf('/') + 1);
        String resolved = service.resolveUrl(code);

        assertEquals("https://openai.com", resolved);
    }

    @Test
    void shouldRespectExpiry() {
        LocalDateTime past = LocalDateTime.now().minusDays(1);
        UrlShortenRequest req = new UrlShortenRequest("https://example.com", null, past);
        UrlShortenResponse resp = service.createShortUrl(req);
        String code = resp.shortUrl().substring(resp.shortUrl().lastIndexOf('/') + 1);

        assertThrows(RuntimeException.class, () -> service.resolveUrl(code));
    }
}
