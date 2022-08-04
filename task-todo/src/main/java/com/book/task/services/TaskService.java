package com.book.task.services;

import com.book.task.dto.TaskDto;
import com.book.task.entities.Task;
import com.book.task.repositories.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class TaskService {
    private TaskRepository taskRepository;

    public TaskService(TaskRepository repository) {
        this.taskRepository = repository;
    }

    public Page<Task> getTasks(Pageable pageable){
        return taskRepository.findAll(pageable);
    }

    public Task getTask(Long taskId){
        Optional<Task> task = taskRepository.findById(taskId);
        return task.get();
    }

    public Task saveTask(TaskDto taskDto){
        var modelMapper = new ModelMapper();
        var task = modelMapper.map(taskDto, Task.class);
        return taskRepository.save(task);
    }
}
