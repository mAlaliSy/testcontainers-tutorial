package dev.alali.testcontainers.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Title is required!")
    @Column(name = "title")
    private String title;

    @Future(message = "Due time should be in future!")
    @Column(name = "due_time")
    private LocalDateTime dueTime;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
