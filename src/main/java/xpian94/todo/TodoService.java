package xpian94.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    @Autowired
    private TodoRepository repo;

    private static int counter = 0;

    TodoResponse create(TodoRequest todoRequest) {
        var entity = TodoEntity.builder()
            .title(todoRequest.getTitle())
            .build();

        var saved = repo.save(entity);

        var response = new TodoResponse();
        response.setId(String.valueOf(saved.getUuid()));

        counter++;

        return response;
    }

    public TodoResponseAll requestAll() {
        var response = new TodoResponseAll();

        repo.findAll()
            .forEach(todoEntity -> response.getTodos().add(new TodoResponse()));

        return response;
    }
}
