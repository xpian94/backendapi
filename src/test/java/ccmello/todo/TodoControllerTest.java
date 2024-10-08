package ccmello.todo;

import ccmello.IntegrationTestBaseWithoutDataSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

class TodoControllerTest extends IntegrationTestBaseWithoutDataSource {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService service;

    @Captor
    ArgumentCaptor<TodoRequest> requestArgumentCaptor;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldCallCreate() throws Exception {
        var requestAsString = objectMapper.writeValueAsString("{}");

        mockMvc.perform(post("/todo")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestAsString)
        ).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(service).create(requestArgumentCaptor.capture());

        var actual = requestArgumentCaptor.getValue();

        assertThat(actual).isNotNull();
    }

    @Test
    void shouldCallRequestAll() throws Exception {
        mockMvc.perform(get("/todo")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        verify(service).requestAll();
    }
}
