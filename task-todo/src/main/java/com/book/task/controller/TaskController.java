package com.book.task.controller;

import com.book.task.dto.TaskDto;
import com.book.task.entities.Task;
import com.book.task.services.TaskService;
import com.book.task.uri.TaskUri;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("api/todo")
@RequiredArgsConstructor
public class TaskController {

    private final PagedResourcesAssembler pagedResourcesAssembler;
    private final TaskService taskService;

    @GetMapping(path = TaskUri.TASKS)
    public ResponseEntity<?> getTasks(TaskDto taskDto, Pageable pageable){
        log.info("TasksController: " + taskDto);
        var events = taskService.getTasks(pageable);
        var resource = pagedResourcesAssembler.toModel(events);
        return ResponseEntity.ok(resource);
    }

    @GetMapping(path = TaskUri.TASK)
    public ResponseEntity<?> getTask(@PathVariable("id") Long taskId){
        try {
            log.info("TasksController:::" + taskId);
            var task = taskService.getTask(taskId);

            Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getTask(taskId)).withSelfRel();
            Link allTasksLink = WebMvcLinkBuilder.linkTo( this.getClass() ).slash("/tasks").withRel("allTasks");
            EntityModel<Task> entityModel = EntityModel.of(task);
            entityModel.add(selfLink, allTasksLink);

            return ResponseEntity.ok(entityModel);
        }catch (RuntimeException exc){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task Not Found", exc);
        }
    }

    @PostMapping(path = TaskUri.CREATE_TASK)
    public ResponseEntity<?> createTask(@RequestBody TaskDto taskDto){
        log.info("TasksController:" + taskDto);
        Task events = taskService.saveTask(taskDto);

        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).createTask(taskDto)).withSelfRel();
        Link allTasksLink = WebMvcLinkBuilder.linkTo( this.getClass() ).slash("/tasks").withRel("allTasks");
        EntityModel<Task> taskResource = EntityModel.of(events);
        taskResource.add(selfLink, allTasksLink);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("CustomResponseHeader", "CustomValue");
        return new ResponseEntity<EntityModel<Task>>(responseHeaders, HttpStatus.CREATED);
    }
}
