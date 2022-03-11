package dev.alali.testcontainers;

import dev.alali.testcontainers.entity.TodoItem;
import dev.alali.testcontainers.repository.TodoItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class TodoAPITest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14.2");

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected TodoItemRepository repository;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @AfterEach
    public void clearDB(){
        repository.deleteAll();
    }

    @Test
    void shouldCreateTodoWhenPostingValidTodo(){
        // given:
        // -- not applicable

        // when
        TodoItem todoToCreate = new TodoItem();
        todoToCreate.setTitle("Test TODO");
        todoToCreate.setDueTime(LocalDateTime.now().plus(3, ChronoUnit.DAYS).truncatedTo(ChronoUnit.MINUTES));

        ResponseEntity<TodoItem> response = restTemplate.postForEntity("/api/v1/todos", todoToCreate, TodoItem.class);

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        TodoItem responseTodo = response.getBody();
        assertNotNull(responseTodo);

        Optional<TodoItem> optionalSavedTodo = repository.findById(responseTodo.getId());
        assertTrue(optionalSavedTodo.isPresent());
        TodoItem savedTodo = optionalSavedTodo.get();

        assertEquals(todoToCreate.getTitle(), savedTodo.getTitle());
        assertEquals(todoToCreate.getDueTime(), savedTodo.getDueTime());

    }


    @Test
    void shouldReturnErrorWhenPostingInvalidTodo(){
        // given:
        // -- not applicable

        // when:
        TodoItem todoToCreate = new TodoItem();
        todoToCreate.setTitle(null);
        todoToCreate.setDueTime(LocalDateTime.now().minus(3, ChronoUnit.DAYS));

        ResponseEntity<TodoItem> response = restTemplate.postForEntity("/api/v1/todos", todoToCreate, TodoItem.class);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        List<TodoItem> allTodos = repository.findAll();
        assertTrue(allTodos.isEmpty());
    }

}
