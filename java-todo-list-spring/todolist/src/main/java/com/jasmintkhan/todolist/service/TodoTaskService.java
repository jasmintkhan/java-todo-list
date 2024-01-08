package com.jasmintkhan.todolist.service;

import com.jasmintkhan.todolist.model.TodoTask;
import com.jasmintkhan.todolist.repository.TodoTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class TodoTaskService {

    private final TodoTaskRepository todoTaskRepository;
    private static final Logger LOGGER = Logger.getLogger(TodoTaskService.class.getName());

    @Autowired
    public TodoTaskService(TodoTaskRepository todoTaskRepository) {
        this.todoTaskRepository = todoTaskRepository;
    }

    // Add a new task
    public TodoTask addTask(TodoTask task) {
        if (task == null) {
            LOGGER.severe("Attempted to add a null task.");
            throw new IllegalArgumentException("Task cannot be null");
        }
        LOGGER.info("Added a new task: " + task.getName());
        return todoTaskRepository.save(task);
    }

    // Delete a task by ID
    public void deleteTaskByID(Long id) {
        LOGGER.info("Attempting to delete task with ID: " + id);

        // Check if ID is null
        if (id == null) {
            LOGGER.warning("Delete operation failed: Provided task ID is null.");
            throw new IllegalArgumentException("Task ID cannot be null.");
        }

        // Check if task exists
        if(!todoTaskRepository.existsById(id)) {
            LOGGER.warning("Delete operation failed: No task found with ID " + id);
            throw new IllegalArgumentException("No task found with ID: " + id);
        }

        // Perform the delete operation
        todoTaskRepository.deleteById(id);
        LOGGER.info("Deleted task with ID: " + id);
    }

    // Delete a task by name
    public void deleteTaskByName(String name) {
        LOGGER.info("Attempting to delete task with name: " + name);

        // Check if name is null or empty
        if (name == null || name.trim().isEmpty()) {
            LOGGER.warning("Delete operation failed: Provided task name is null or empty.");
            throw new IllegalArgumentException("Task name cannot be null or empty.");
        }

        // Find and delete the task
        List<TodoTask> tasks = todoTaskRepository.findAll();
        Optional<TodoTask> taskToDelete = tasks.stream()
                .filter(task -> task.getName().equalsIgnoreCase(name))
                .findFirst();
        
        if(taskToDelete.isPresent()) {
            todoTaskRepository.deleteById(taskToDelete.get().getID());
            LOGGER.info("Deleted task with name: " + name);
        } else {
            LOGGER.warning("Delete operation failed: No task found with name " + name);
            throw new IllegalArgumentException("No task found with name: " + name);
        }
    }


    // Update a task by name
    public TodoTask updateTaskByName(String currentName, TodoTask updatedTask) {
        LOGGER.info("Attempting to update task: " + currentName);
    
        // Validate input task details
        if (updatedTask == null) {
            LOGGER.severe("Provided updated task is null");
            throw new IllegalArgumentException("Updated task cannot be null.");
        }
        String newName = updatedTask.getName();
        String newDescription = updatedTask.getDescription();
        LocalDate newDueDate = updatedTask.getDueDate();
        TodoTask.Priority newPriority = updatedTask.getPriority();
        TodoTask.Category newCategory = updatedTask.getCategory();
    
        // Check if the task with the given name exists
        Optional<TodoTask> existingTaskOptional = todoTaskRepository.findAll().stream()
                .filter(task -> task.getName().equals(currentName))
                .findFirst();
    
        if (!existingTaskOptional.isPresent()) {
            LOGGER.warning("Update failed: No task found with name: " + currentName);
            throw new IllegalArgumentException("No task found with name: " + currentName);
        }
    
        TodoTask existingTask = existingTaskOptional.get();
    
        // Validate newName
        if (newName == null || newName.trim().isEmpty()) {
            LOGGER.severe("Attempted to update with a null or empty task name.");
            throw new IllegalArgumentException("Task name cannot be null or empty.");
        }
    
        // Validate newDescription
        if (newDescription == null) {
            LOGGER.severe("Attempted to update with a null task description.");
            throw new IllegalArgumentException("Task description cannot be null.");
        }
    
        // Validate newDueDate
        if (newDueDate != null && newDueDate.isBefore(LocalDate.now())) {
            LOGGER.severe("Attempted to update with a due date in the past.");
            throw new IllegalArgumentException("Due date cannot be in the past.");
        }
    
        // Validate newPriority
        if (newPriority == null) {
            LOGGER.severe("Attempted to update with a null task priority.");
            throw new IllegalArgumentException("Priority cannot be null.");
        }
    
        // Validate newCategory
        if (newCategory == null) {
            LOGGER.severe("Attempted to update with a null task category.");
            throw new IllegalArgumentException("Category cannot be null.");
        }
    
        // Update the task
        existingTask.setName(newName);
        existingTask.setDescription(newDescription);
        existingTask.setDueDate(newDueDate);
        existingTask.setCompleted(updatedTask.isCompleted());
        existingTask.setPriority(newPriority);
        existingTask.setCategory(newCategory);
    
        TodoTask updated = todoTaskRepository.save(existingTask);
        LOGGER.info("Task updated successfully: " + currentName + " to " + newName);
        return updated;
    }
    
    // Update a task by ID
    public Optional<TodoTask> updateTaskById(Long id, TodoTask updatedTask) {
        LOGGER.info("Attempting to update task with ID: " + id);

        // Validate input task details
        if (updatedTask == null) {
            LOGGER.severe("Provided updated task is null");
            throw new IllegalArgumentException("Updated task cannot be null.");
        }

        // Check if the task with the given ID exists
        Optional<TodoTask> taskOptional = todoTaskRepository.findById(id);
        if (taskOptional.isPresent()) {
            TodoTask task = taskOptional.get();

            // Update the task
            task.setName(updatedTask.getName());
            task.setDescription(updatedTask.getDescription());
            task.setDueDate(updatedTask.getDueDate());
            task.setCompleted(updatedTask.isCompleted());
            task.setPriority(updatedTask.getPriority());
            task.setCategory(updatedTask.getCategory());

            TodoTask updated = todoTaskRepository.save(task);
            LOGGER.info("Task updated successfully: Using task ID[" + id + "]");

            return Optional.of(updated);
        } else {
            LOGGER.warning("Update failed: No task found with ID: " + id);
            return Optional.empty();
        }
    }

    // Filters tasks by priority
    public List<TodoTask> filterTasksByPriority(TodoTask.Priority priority) {
        LOGGER.info("Filtering tasks by priority: " + priority);
        
        // Validate priority
        if (priority == null) {
            LOGGER.severe("Attempted to filter by a null task priority.");
            throw new IllegalArgumentException("Priority cannot be null.");
        }
        
        // Perform the filtering
        List<TodoTask> filteredTasks = todoTaskRepository.findAll().stream()
                    .filter(task -> task.getPriority() == priority)
                    .collect(Collectors.toList());
        
        // Log the results
        LOGGER.info("Number of tasks found with priority " + priority + ": " + filteredTasks.size());
        return filteredTasks;
    }

    // Filters tasks by category
    public List<TodoTask> filterTasksByCategory(TodoTask.Category category) {
        LOGGER.info("Filtering tasks by category: " + category);

        // Validate category
        if (category == null) {
            LOGGER.severe("Attempted to filter by a null task category.");
            throw new IllegalArgumentException("Category cannot be null.");
        }

        // Perform the filtering
        List<TodoTask> filteredTasks = todoTaskRepository.findAll().stream()
                    .filter(task -> task.getCategory() == category)
                    .collect(Collectors.toList());

        // Log the results
        LOGGER.info("Number of tasks found with category " + category + ": " + filteredTasks.size());
        return filteredTasks;
    }

    // Filters tasks by due date
    public List<TodoTask> filterTasksByDueDate(LocalDate dueDate) {
        LOGGER.info("Filtering tasks by due date: " + dueDate);

        // Validate dueDate
        if (dueDate == null) {
            LOGGER.severe("Attempted to filter by a null due date.");
            throw new IllegalArgumentException("Due date cannot be null.");
        }

        // Perform the filtering
        List<TodoTask> filteredTasks = todoTaskRepository.findAll().stream()
                    .filter(task -> task.getDueDate() != null && task.getDueDate().equals(dueDate))
                    .collect(Collectors.toList());

        // Log the results
        LOGGER.info("Number of tasks found with due date " + dueDate + ": " + filteredTasks.size());
        return filteredTasks;
    }

    // Filters tasks by completion status
    public List<TodoTask> filterTasksByCompletionStatus(boolean isCompleted) {
        LOGGER.info("Filtering tasks by completion status: " + isCompleted);
        List<TodoTask> filteredTasks = todoTaskRepository.findAll().stream()
                .filter(task -> task.isCompleted() == isCompleted)
                .collect(Collectors.toList());
        LOGGER.info("Number of tasks found: " + filteredTasks.size());
        return filteredTasks;
    }

    public List<TodoTask> filterTasks(TodoTask.Priority priority, TodoTask.Category category, LocalDate dueDate, Boolean isCompleted) {
        Stream<TodoTask> taskStream = todoTaskRepository.findAll().stream();
    
        if (priority != null) {
            taskStream = taskStream.filter(task -> task.getPriority() == priority);
        }
        if (category != null) {
            taskStream = taskStream.filter(task -> task.getCategory() == category);
        }
        if (dueDate != null) {
            taskStream = taskStream.filter(task -> task.getDueDate().equals(dueDate));
        }
        if (isCompleted != null) {
            taskStream = taskStream.filter(task -> task.isCompleted() == isCompleted);
        }
    
        return taskStream.collect(Collectors.toList());
    }
    
    // Sorts tasks by priority
    public List<TodoTask> sortTasksByPriority() {
        LOGGER.info("Sorting tasks by priority.");
        List<TodoTask> sortedTasks = todoTaskRepository.findAll().stream()
                .sorted(Comparator.comparing(TodoTask::getPriority, Comparator.nullsLast(Enum::compareTo)))
                .collect(Collectors.toList());
        LOGGER.info("Sorting completed.");
        return sortedTasks;
    }

    // Sorts tasks by category
    public List<TodoTask> sortTasksByCategory() {
        LOGGER.info("Sorting tasks by category.");
        List<TodoTask> sortedTasks = todoTaskRepository.findAll().stream()
                .sorted(Comparator.comparing(TodoTask::getCategory, Comparator.nullsLast(Enum::compareTo)))
                .collect(Collectors.toList());
        LOGGER.info("Sorting completed.");
        return sortedTasks;
    }

    // Sorts tasks by due date
    public List<TodoTask> sortTasksByDueDate() {
        LOGGER.info("Sorting tasks by due date.");
        List<TodoTask> sortedTasks = todoTaskRepository.findAll().stream()
                .sorted(Comparator.comparing(TodoTask::getDueDate, Comparator.nullsLast(LocalDate::compareTo)))
                .collect(Collectors.toList());
        LOGGER.info("Sorting completed.");
        return sortedTasks;
    }

    // Sorts tasks by name
    public List<TodoTask> sortTasksByName() {
        LOGGER.info("Sorting tasks by name.");
        List<TodoTask> sortedTasks = todoTaskRepository.findAll().stream()
                .sorted(Comparator.comparing(TodoTask::getName, Comparator.nullsLast(String::compareTo)))
                .collect(Collectors.toList());
        LOGGER.info("Sorting completed.");
        return sortedTasks;
    }

    public List<TodoTask> sortTasks(String sortBy, String order) {
        List<TodoTask> tasks = findAllTasks(); // Assuming you have a method to get all tasks
    
        Comparator<TodoTask> comparator;
        switch (sortBy.toLowerCase()) {
            case "priority":
                comparator = Comparator.comparing(TodoTask::getPriority);
                break;
            case "category":
                comparator = Comparator.comparing(TodoTask::getCategory);
                break;
            case "duedate":
                comparator = Comparator.comparing(TodoTask::getDueDate);
                break;
            case "name":
                comparator = Comparator.comparing(TodoTask::getName);
                break;
            default:
                throw new IllegalArgumentException("Invalid sort criteria: " + sortBy);
        }
    
        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }
    
        return tasks.stream().sorted(comparator).collect(Collectors.toList());
    }
    

    // Find all tasks: RETURNS ALL TASKS
    public List<TodoTask> findAllTasks() {
        LOGGER.info("Fetching all tasks.");
        List<TodoTask> tasks = todoTaskRepository.findAll();
        LOGGER.info("Number of tasks retrieved: " + tasks.size());
        return tasks;
    }

    // Find a task by ID: FIND IS SPECIFIC TO A TASK
    public Optional<TodoTask> findTaskById(Long id) {
        LOGGER.info("Searching for task with ID: " + id);
    
        // Validate the id
        if (id == null) {
            LOGGER.warning("Task ID provided is null.");
            throw new IllegalArgumentException("Task ID cannot be null.");
        }
        
        return todoTaskRepository.findById(id);
    }
    

    // Search tasks by name (case-insensitive, partial match): SEARCH IS GENERAL/BROAD
    public List<TodoTask> searchTasksByName(String query) {
        LOGGER.info("Initiating search for tasks with query: '" + query + "'.");

        // Check for null or empty query
        if (query == null || query.trim().isEmpty()) {
            LOGGER.warning("Search query was null or empty.");
            throw new IllegalArgumentException("Search query cannot be null or empty.");
        }

        // Perform the search
        String lowerCaseQuery = query.toLowerCase();
        List<TodoTask> searchResults = todoTaskRepository.findAll().stream()
                .filter(task -> task.getName().toLowerCase().contains(lowerCaseQuery))
                .collect(Collectors.toList());

        // Log the results
        LOGGER.info("Number of tasks found containing '" + query + "': " + searchResults.size());
        return searchResults;
    }


}
