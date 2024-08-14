package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class TodoControllerTest extends IntegrationTestBaseWithoutDataSource {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService service;

    @Test
    void shouldCreate() throws Exception {
        mockMvc.perform(post("/todo"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

        doNothing().when(service).create();

        verify(service).create();
    }
}
