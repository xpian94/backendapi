package xpian94.todo;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoRequest {
    private String title;
}
