package todolist;

import todolist.controller.TodoTaskController;
import todolist.model.TodoTask;
import todolist.model.TodoTask.Priority;
import todolist.model.TodoTask.Category;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class TodoTaskApp {

    private final TodoTaskController controller;
    private final Scanner scanner;

    public TodoTaskApp(TodoTaskController controller) {
        this.controller = new TodoTaskController();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("Choose an option: \n1. Add Task \n2. Delete Task \n3. Update Task \n4. List Tasks \n5. Exit");
            int option = scanner.nextInt();
            scanner.nextLine(); 

            switch (option) {
                case 1:
                    addTask();
                    break;
                case 2:
                    deleteTask();
                    break;
                case 3:
                    updateTask();
                    break;
                case 4:
                    listTasks();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
        System.out.println("Thank you for using my Todo List Application!");
    }

    private void addTask() {
        System.out.println("Enter task name:");
        String name = scanner.nextLine();
    
        System.out.println("Enter task description:");
        String description = scanner.nextLine();
    
        System.out.println("Enter task due date (yyyy-MM-dd):");
        String dueDateString = scanner.nextLine();
        LocalDate dueDate = LocalDate.parse(dueDateString);
    
        System.out.println("Enter task priority (LOW, MEDIUM, HIGH):");
        String priorityString = scanner.nextLine();
        Priority priority = Priority.valueOf(priorityString.toUpperCase());
    
        System.out.println("Enter task category (WORK, STUDY, PERSONAL, HEALTH):");
        String categoryString = scanner.nextLine();
        Category category = Category.valueOf(categoryString.toUpperCase());
    
        TodoTask newTask = new TodoTask(name, description, dueDate, priority, category);
        controller.addTask(newTask);
        System.out.println("Task added successfully!");
    }
    
    private void deleteTask() {
        System.out.println("Enter task ID to delete:");
        int taskId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        controller.deleteTaskByID(taskId);
        System.out.println("Task deleted successfully!");
    }
    
    private void updateTask() {
        System.out.println("Enter task ID to update:");
        int taskId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
    
        System.out.println("Enter new task name:");
        String newName = scanner.nextLine();
    
        System.out.println("Enter new task description:");
        String newDescription = scanner.nextLine();
    
        System.out.println("Enter new task due date (yyyy-MM-dd):");
        String newDueDateString = scanner.nextLine();
        LocalDate newDueDate = LocalDate.parse(newDueDateString);
    
        System.out.println("Enter new task priority (LOW, MEDIUM, HIGH):");
        String newPriorityString = scanner.nextLine();
        Priority newPriority = Priority.valueOf(newPriorityString.toUpperCase());
    
        System.out.println("Enter new task category (WORK, STUDY, PERSONAL, HEALTH):");
        String newCategoryString = scanner.nextLine();
        Category newCategory = Category.valueOf(newCategoryString.toUpperCase());
    
        controller.updateTaskByID(taskId, newName, newDescription, newDueDate, false, newPriority, newCategory);
        System.out.println("Task updated successfully!");
    }

    private void displayTasks(List<TodoTask> tasks) {
        for (TodoTask task : tasks) {
            System.out.println(task);
        }
    }

    private void filterTasks() {
        System.out.println("Filter by: 1. Priority 2. Category 3. Due Date 4. Completion Status");
        // Get user input and call the respective method in the controller
        // For example, if user chooses Priority:
        System.out.println("Enter priority (LOW, MEDIUM, HIGH):");
        String priorityString = scanner.nextLine();
        Priority priority = Priority.valueOf(priorityString.toUpperCase());
        displayTasks(controller.filterTasksByPriority(priority));
    }
    
    private void sortTasks() {
        System.out.println("Sort by: 1. Priority 2. Category 3. Due Date 4. Name");
        // Get user input and call the respective method in the controller
        // For example, if user chooses Priority:
        displayTasks(controller.sortTasksByPriority());
    }
    
    
    private void listTasks() {
        System.out.println("Choose an option:");
    System.out.println("1. List all tasks");
    System.out.println("2. Filter tasks");
    System.out.println("3. Sort tasks");
    int choice = scanner.nextInt();
    scanner.nextLine(); // consume the newline

    switch (choice) {
        case 1:
            displayTasks(controller.findAllTasks());
            break;
        case 2:
            filterTasks();
            break;
        case 3:
            sortTasks();
            break;
        default:
            System.out.println("Invalid choice");
    }
    }
    

    public static void main(String[] args) {
        TodoTaskController controller = new TodoTaskController();
        TodoTaskApp app = new TodoTaskApp(controller);
    
        // Starting the notification service
        TodoTaskController.NotificationService notificationService = new TodoTaskController.NotificationService(controller);
        notificationService.startChecking();
    
        app.start();
    }
}
