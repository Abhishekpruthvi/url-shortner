package com.algospire.url.shortner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.algospire.url.shortner.dto.UrlShortenRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UrlShortenerControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    void shortenEndpointShouldReturnShortUrl() throws Exception {
        UrlShortenRequest req = new UrlShortenRequest("https://spring.io", null);

        mvc.perform(post("/api/shorten")
                .contentType("application/json")
                .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortUrl", containsString("/api/")));
    }
}
