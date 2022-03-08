package dev.alali.testcontainers.repository;

import dev.alali.testcontainers.entity.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {

}
