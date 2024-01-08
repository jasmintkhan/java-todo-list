package com.jasmintkhan.todolist.controller;

import com.jasmintkhan.todolist.model.TodoTask;
import com.jasmintkhan.todolist.service.TodoTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TodoTaskController {

    private final TodoTaskService todoTaskService;

    @Autowired
    public TodoTaskController(TodoTaskService todoTaskService) {
        this.todoTaskService = todoTaskService;
    }

    // Get all tasks
    @GetMapping
    public List<TodoTask> getAllTasks() {
        return todoTaskService.findAllTasks();
    }

    // Get a task by ID
    @GetMapping("/{id}")
    public ResponseEntity<TodoTask> getTaskById(@PathVariable Long id) {
        return todoTaskService.findTaskById(id)
                .map(task -> ResponseEntity.ok(task))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get a task by name
    @GetMapping("/tasks/name/{name}")
    public ResponseEntity<List<TodoTask>> getTasksByName(@PathVariable String name) {
        List<TodoTask> tasks = todoTaskService.searchTasksByName(name);
        if (tasks.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tasks);
    }

    // Add a new task
    @PostMapping
    public TodoTask addTask(@RequestBody TodoTask task) {
        return todoTaskService.addTask(task);
    }

    // Update a task by ID
    @PutMapping("/{id}")
    public ResponseEntity<TodoTask> updateTaskById(@PathVariable Long id, @RequestBody TodoTask updatedTask) {
        return todoTaskService.updateTaskById(id, updatedTask)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update a task by name
    @PutMapping("/tasks/updateByName")
    public ResponseEntity<TodoTask> updateTaskByName(
            @RequestParam String currentName,
            @RequestBody TodoTask updatedTask) {

        try {
            TodoTask updated = todoTaskService.updateTaskByName(currentName, updatedTask);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Delete a task by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id) {
        todoTaskService.deleteTaskByID(id);
        return ResponseEntity.ok().build();
    }

    // Delete a task by name
    @DeleteMapping("/tasks/deleteByName")
    public ResponseEntity<String> deleteTaskByName(@RequestParam String name) {
        try {
            todoTaskService.deleteTaskByName(name);
            return ResponseEntity.ok("Task with name '" + name + "' deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    // Filter tasks by priority, category, due date, and/or completion status
    @GetMapping("/tasks/filter")
    public ResponseEntity<List<TodoTask>> filterTasks(
            @RequestParam(required = false) TodoTask.Priority priority,
            @RequestParam(required = false) TodoTask.Category category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate,
            @RequestParam(required = false) Boolean isCompleted) {
        List<TodoTask> filteredTasks = todoTaskService.filterTasks(priority, category, dueDate, isCompleted);
        return ResponseEntity.ok(filteredTasks);
    }

    // Sort tasks by name, due date, priority, or category
    @GetMapping("/tasks/sort")
    public ResponseEntity<List<TodoTask>> sortTasks(
        @RequestParam String sortBy, 
        @RequestParam(required = false, defaultValue = "asc") String order) {

        List<TodoTask> sortedTasks = todoTaskService.sortTasks(sortBy, order);
        return ResponseEntity.ok(sortedTasks);
    }

}
