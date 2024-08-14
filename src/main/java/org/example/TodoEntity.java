package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TodoEntity {
    @Id
    private Long id;
}
