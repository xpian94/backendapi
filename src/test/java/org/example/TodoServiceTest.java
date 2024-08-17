package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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

    @Captor
    private ArgumentCaptor<TodoEntity> entityArgumentCaptor;

    @Test
    void canCreate() {
        var request = new TodoRequest();
        request.setTitle("A title");

        createAndAssert("99", request);
        reset(repo);

        request = new TodoRequest();
        request.setTitle("Another title");

        createAndAssert("100", request);
    }

    private void createAndAssert(String expectedId, TodoRequest request) {
        var entity = new TodoEntity();
        entity.setTitle(request.getTitle());
        entity.setId(Long.valueOf(expectedId));

        when(repo.save(any())).thenReturn(entity);

        var response = service.create(request);

        verify(repo).save(entityArgumentCaptor.capture());

        var actual = entityArgumentCaptor.getValue();

        assertThat(response.getId()).isEqualTo(expectedId);
        assertThat(actual.getTitle()).isEqualTo(request.getTitle());
    }
}