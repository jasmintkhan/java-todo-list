package com.jasmintkhan.todolist.repository;

import com.jasmintkhan.todolist.model.TodoTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoTaskRepository extends JpaRepository<TodoTask, Long> {
    // Custom query methods
    List<TodoTask> findByCategory(TodoTask.Category category);
    List<TodoTask> findByIsCompleted(boolean isCompleted);

    // More custom methods can be added as needed
}
