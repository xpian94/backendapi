package ccmello.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TodoRepositoryIntegrationTest {
    @Autowired
    private TodoRepository repo;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void givenNewTodoEntity_whenPersist_thenIdIsIncremented() {
        var todoEntity = new TodoEntity();
        var saved = repo.save(todoEntity);

        var oldUuid = saved.getUuid();

        assertThat(saved.getId()).isEqualTo(1L);
        assertThat(oldUuid).isInstanceOf(UUID.class);

        todoEntity = new TodoEntity();
        saved = repo.save(todoEntity);

        var newUuid = saved.getUuid();

        assertThat(saved.getId()).isEqualTo(2L);
        assertThat(newUuid).isNotEqualTo(oldUuid);
    }

    @Test
    void giveNewTodoEntity_whenPersist_thenSuccess() {
        var todoEntity = new TodoEntity();

        var saved = repo.save(todoEntity);
        var found = entityManager.find(TodoEntity.class, saved.getId());

        assertThat(found).isEqualTo(todoEntity);
    }
}
