package ccmello.todo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "todo")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private UUID uuid;

    private String title;

    @PrePersist
    protected void onCreate() {
        uuid = java.util.UUID.randomUUID();
    }
}
