package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestSanityCheckTest extends IntegrationTestBaseWithoutDataSource {
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private TodoService service;

    @Test
    void shouldReturnDefaultMessage() {
        String url = "http://localhost:%d%s".formatted(port, "/");
        var response = restTemplate.getForObject(url, String.class);
        assertThat(response).contains("Hello, World");
    }
}
