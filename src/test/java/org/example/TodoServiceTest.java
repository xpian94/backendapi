package org.example;

import org.junit.jupiter.api.Test;

public class TodoServiceTest {
    private final TodoService service = new TodoService();

    @Test
    void nothing() {
        service.create();
    }
}
