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

        return TodoResponse.builder()
            .id(String.valueOf(saved.getUuid()))
            .build();
    }

    public TodoResponseAll requestAll() {
        var response = new TodoResponseAll();

        repo.findAll()
            .forEach(todoEntity -> {
                var todoResponse = TodoResponse.builder()
                    .id(todoEntity.getUuid().toString())
                    .title(todoEntity.getTitle())
                    .build();

                response.getTodos().add(todoResponse);
            });

        return response;
    }
}
