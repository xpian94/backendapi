package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class HttpRequestSanityCheckTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private TodoService service;

    @LocalServerPort
    private int port;

    @Test
    void shouldReturnDefaultMessage() {
        String url = "http://localhost:%d%s".formatted(port, "/");
        var response = restTemplate.getForObject(url, String.class);
        assertThat(response).contains("Hello, World");
    }
}
