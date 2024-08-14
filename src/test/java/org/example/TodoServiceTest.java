package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class TodoServiceTest {
    @InjectMocks
    private TodoService service;

    @MockBean
    private TodoRepository repo;

    @Test
    void canCreate() {
        when(repo.save(any())).thenReturn(new Object());

        service.create();

        verify(repo).save(any());
    }
}