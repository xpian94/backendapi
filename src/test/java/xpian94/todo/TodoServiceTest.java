package xpian94.todo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

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

    private void createAndAssert(UUID expectedId, TodoRequest request) {
        var entity = TodoEntity.builder()
            .title(request.getTitle())
            .uuid(expectedId)
            .build();

        when(repo.save(any())).thenReturn(entity);

        var response = service.create(request);

        verify(repo).save(entityArgumentCaptor.capture());

        var actual = entityArgumentCaptor.getValue();

        assertThat(response.getId()).isEqualTo(expectedId.toString());
        assertThat(actual.getTitle()).isEqualTo(request.getTitle());
    }

    @Test
    void canCreate() {
        var request = TodoRequest.builder()
            .title("A title")
            .build();

        var uuid = UUID.randomUUID();

        createAndAssert(uuid, request);
        reset(repo);

        request = TodoRequest.builder()
            .title("Another title")
            .build();

        uuid = UUID.randomUUID();

        createAndAssert(uuid, request);
    }

    @Test
    void canListAll() {
        when(repo.findAll()).thenReturn(List.of());

        var response = service.requestAll();

        assertThat(response.getTodos().size()).isZero();
        verify(repo).findAll();

        var entity = TodoEntity.builder()
            .uuid(UUID.randomUUID())
            .title("A title")
            .build();

        when(repo.findAll()).thenReturn(List.of(entity));

        response = service.requestAll();

        var todos = response.getTodos();

        assertThat(todos.size()).isOne();

        var first = todos.getFirst();

        assertThat(first).isInstanceOf(TodoResponse.class);

        assertThat(first.getId()).isEqualTo(entity.getUuid().toString());
        assertThat(first.getTitle()).isEqualTo(entity.getTitle());
    }
}