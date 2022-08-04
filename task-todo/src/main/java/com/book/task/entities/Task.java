package com.book.task.entities;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @NotNull(message = "{NotNull.Task.name}")
    private String name;

    @Column
    @NotNull(message = "{NotNull.Task.description}")
    private String description;

}
