package xpian94.todo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import xpian94.IntegrationTestBase;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@AutoConfigureTestEntityManager
public class TodoIntegrationTest extends IntegrationTestBase {
    @Autowired
    private TestRestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void givenRequestBody_whenCreate_thenSuccess() throws JsonProcessingException {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var body = """
             {
                "title": "A title"
             }
            """;

        var request = new HttpEntity<>(body, headers);

        var url = "http://localhost:%d%s".formatted(port, "/todo");
        var response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        var root = objectMapper.readTree(response.getBody());
        var idNodePath = root.path("id");
        var id = idNodePath.asText();

        assertThat(root).isNotNull();
        assertThat(idNodePath).isNotNull();

        var entityManager = testEntityManager.getEntityManager();

        var entity = (TodoEntity) entityManager
            .createQuery("SELECT t FROM todo t WHERE t.uuid = :uuid")
            .setParameter("uuid", UUID.fromString(id)).getSingleResult();

        assertThat(entity).isNotNull();
    }
}
