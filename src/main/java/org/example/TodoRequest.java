package org.example;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoRequest {
    private String title;
}
