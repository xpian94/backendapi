package xpian94.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    @Autowired
    private TodoRepository repo;

    TodoResponse create(TodoRequest todoRequest) {
        var entity = TodoEntity.builder()
            .title(todoRequest.getTitle())
            .build();

        var saved = repo.save(entity);

        var response = new TodoResponse();
        response.setId(String.valueOf(saved.getUuid()));

        return response;
    }
}
