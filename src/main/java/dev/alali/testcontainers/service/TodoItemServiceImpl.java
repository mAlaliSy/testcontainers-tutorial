package dev.alali.testcontainers.service;

import dev.alali.testcontainers.entity.TodoItem;
import dev.alali.testcontainers.repository.TodoItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoItemServiceImpl implements TodoItemService {

    private final TodoItemRepository repository;

    @Override
    public List<TodoItem> getTodos() {
        return repository.findAll();
    }

    @Override
    public TodoItem createTodo(TodoItem todo) {
        return repository.save(todo);
    }
}
