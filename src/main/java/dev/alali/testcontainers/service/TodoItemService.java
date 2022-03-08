package dev.alali.testcontainers.service;

import dev.alali.testcontainers.entity.TodoItem;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TodoItemService {

    List<TodoItem> getTodos();

    TodoItem createTodo(TodoItem todo);

}
