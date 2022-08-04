package com.book.task.dto;

import lombok.Data;

@Data
public class TaskDto {
    private Long id;
    private String name;
    private String description;
    private String createdDate;
}
