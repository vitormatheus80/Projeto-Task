package com.book.task.service;

import com.book.task.entities.Task;
import com.book.task.mock.TaskMock;
import com.book.task.repositories.TaskRepository;
import com.book.task.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;
    private TaskService taskService;

    @BeforeEach
    public void setup(){
        taskService = new TaskService(taskRepository);
        PageRequest pageRequest = PageRequest.of(0,5, Sort.by(
                Sort.Order.asc("name"),
                Sort.Order.desc("id")));
        Mockito.lenient().when(taskRepository.findAll(pageRequest)).thenReturn(TaskMock.createTasks());
    }

    @Test
    @DisplayName("Should return all tasks")
    public void getTasks_happypath(){
        PageRequest pageRequest = PageRequest.of(0,5,Sort.by(
                Sort.Order.asc("name"),
                Sort.Order.desc("id")));
        Page<Task> tasks = taskService.getTasks((pageRequest));

        assertEquals(tasks.getTotalPages(), 1);
        assertEquals(tasks.getNumberOfElements(), 2);
        assertNotNull(tasks);
    }

    @Nested
    @DisplayName("Happy Tests")
    class happycases{
        @Test
        void justtest(){
            String name = "just testing";
            assertEquals(name, "just testing");
        }

        @Test
        void justtest1(){
            String name = "just testin";
            assertEquals(name, "just testin");
        }
    }

    @Nested
    @DisplayName("Unhappy Tests")
    class unhappycases{
        @Test
        void justtest(){
            String name = "just testing";
            assertEquals(name, "just testing");
        }

        @Test
        void justtest1(){
            String name = "just testing";
            assertEquals(name, "just testing");
        }
    }
}
