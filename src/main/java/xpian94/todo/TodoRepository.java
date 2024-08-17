package xpian94.todo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TodoRepository extends CrudRepository<TodoEntity, Long> {
    TodoEntity findByUuid(UUID uuid);
}
