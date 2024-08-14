package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class TodoServiceTest {
    @InjectMocks
    private TodoService service;

    @MockBean
    private TodoRepository repo;

    @Test
    void canCreate() {
        createAndAssert("1");
        reset(repo);
        createAndAssert("2");
    }

    private void createAndAssert(String expectedId) {
        var entity = new TodoEntity();
        entity.setTitle("A title");
        entity.setId(Long.valueOf(expectedId));

        var request = new TodoRequest();
        request.setTitle("A title");

        when(repo.save(any())).thenReturn(entity);

        var response = service.create(request);

        verify(repo).save(any());

        assertThat(response.getId()).isEqualTo(expectedId);
    }
}