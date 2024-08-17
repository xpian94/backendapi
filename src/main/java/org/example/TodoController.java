package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TodoController {
    @Autowired
    private TodoService service;

    @RequestMapping(value = "/todo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<TodoResponse> create() {
        var response = service.create(new TodoRequest());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
