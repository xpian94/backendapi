package xpian94.todo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TodoResponse {
    private String id;

    private String title;
}
