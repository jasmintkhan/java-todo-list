package com.jasmintkhan.todolist.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jasmintkhan.todolist.TodolistApplication;
import com.jasmintkhan.todolist.model.TodoTask;
import com.jasmintkhan.todolist.repository.TodoTaskRepository;
import com.jasmintkhan.todolist.service.TodoTaskService;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest(classes = TodolistApplication.class)
public class TodoTaskServiceTest {

    @Autowired
    private TodoTaskService service;

    @MockBean
    private TodoTaskRepository repository;

    @Test
    public void whenAddTask_thenTaskShouldBeSaved() {
        TodoTask task = new TodoTask("Test", "Description", LocalDate.now(), TodoTask.Priority.HIGH, TodoTask.Category.WORK);
        when(repository.save(any(TodoTask.class))).thenReturn(task);

        TodoTask created = service.addTask(task);

        assertThat(created.getName()).isSameAs(task.getName());
        verify(repository).save(task);
    }
}

