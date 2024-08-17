package xpian94.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TodoRepositoryIntegrationTest {
    @Autowired
    private TodoRepository repo;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void givenNewTodoEntity_whenPersist_thenIdIsIncremented() {
        var todoEntity = new TodoEntity();
        var saved = repo.save(todoEntity);

        assertThat(saved.getId()).isEqualTo(1L);

        todoEntity = new TodoEntity();
        saved = repo.save(todoEntity);

        assertThat(saved.getId()).isEqualTo(2L);
    }

    @Test
    void giveNewTodoEntity_whenPersist_thenSuccess() {
        var todoEntity = new TodoEntity();

        var saved = repo.save(todoEntity);
        var found = entityManager.find(TodoEntity.class, saved.getId());

        assertThat(found).isEqualTo(todoEntity);
    }
}
