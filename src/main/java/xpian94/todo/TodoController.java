package xpian94.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TodoController {
    @Autowired
    private TodoService service;

    @RequestMapping(value = "/todo")
    ResponseEntity<TodoResponse> create(@RequestBody TodoRequest request) {
        var response = service.create(request);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(response);
    }

    @RequestMapping(value = "/todo", method = RequestMethod.GET)
    ResponseEntity<TodoResponseAll> all() {
        var response = service.requestAll();

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(response);
    }
}
