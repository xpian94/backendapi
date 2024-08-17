package ccmello.todo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import ccmello.IntegrationTestBase;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TodoIntegrationTest extends IntegrationTestBase {
    @Autowired
    private TestRestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private TodoRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    private ResponseEntity<String> requestTodoCreation() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var body = """
             {
                "title": "A title"
             }
            """;

        var request = new HttpEntity<>(body, headers);

        var url = "http://localhost:%d%s".formatted(port, "/todo");

        return restTemplate.exchange(url, HttpMethod.POST, request, String.class);
    }

    private ResponseEntity<String> requestAllTodos() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var request = new HttpEntity<>("", headers);

        var url = "http://localhost:%d%s".formatted(port, "/todo");
        return restTemplate.exchange(url, HttpMethod.GET, request, String.class);
    }

    private void verifyingTodosListSize(ResponseEntity<String> response, int expectedSize) throws JsonProcessingException {
        var root = objectMapper.readTree(response.getBody());
        var todos = root.findPath("todos");

        assertThat(todos.size()).isEqualTo(expectedSize);
    }

    @Test
    void givenRequestBody_whenCreate_thenSuccess() throws JsonProcessingException {
        var response = requestTodoCreation();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        var root = objectMapper.readTree(response.getBody());
        var idNodePath = root.path("id");
        var id = idNodePath.asText();

        assertThat(root).isNotNull();
        assertThat(idNodePath).isNotNull();

        var found = repository.findByUuid(UUID.fromString(id));

        assertThat(found).isNotNull();
    }

    @Test
    void givenCreatedTodo_whenRequestListAll_thenSuccess() throws JsonProcessingException {
        var response = requestAllTodos();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        verifyingTodosListSize(response, 0);

        requestTodoCreation();

        response = requestAllTodos();

        verifyingTodosListSize(response, 1);
    }
}
