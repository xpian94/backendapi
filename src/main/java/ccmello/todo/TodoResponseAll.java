package ccmello.todo;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TodoResponseAll {
    private final List<TodoResponse> todos = new ArrayList<>();
}
