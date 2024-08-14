package org.example;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TodoController {
    @RequestMapping(value = "/todo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    String create() {
        return "";
    }
}
