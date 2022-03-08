package dev.alali.testcontainers.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "due_time")
    private LocalDateTime dueTime;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
