package com.jasmintkhan.todolist.repository;

import com.jasmintkhan.todolist.model.TodoTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoTaskRepository extends JpaRepository<TodoTask, Long> {
    // You can define custom query methods here if needed
}
