package dev.alali.testcontainers.api;

import dev.alali.testcontainers.entity.TodoItem;
import dev.alali.testcontainers.service.TodoItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TodoAPI {

    private final TodoItemService service;

    @GetMapping("/todos")
    public ResponseEntity<List<TodoItem>> getTodos() {
        return ResponseEntity.ok(service.getTodos());
    }

    @PostMapping("/todos")
    public ResponseEntity<TodoItem> create(@Valid @RequestBody TodoItem todo) {
        TodoItem saved = service.createTodo(todo);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

}
