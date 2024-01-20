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
    private TodoTaskController.NotificationService notificationService;

    public TodoTaskApp(TodoTaskController controller) {
        this.controller = new TodoTaskController();
        this.scanner = new Scanner(System.in);
        this.notificationService = new TodoTaskController.NotificationService(controller);

    }

    public void start() {
        // Starting the notification service
        
        notificationService.startChecking();
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
                    exitApplication();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
        System.out.println("Thank you for using my Todo List Application!");
    }

    private void exitApplication() {
        notificationService.stopChecking(); // Now accessible here
        System.out.println("Thank you for using my Todo List Application!");
        System.exit(0);
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
        System.out.println("Filter by: \n1. Priority \n2. Category \n3. Due Date \n4. Completion Status");
        System.out.print("Your choice: ");
        int filterChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the remaining newline
    
        switch (filterChoice) {
            case 1:
                // Filter by Priority
                System.out.println("Enter priority (LOW, MEDIUM, HIGH):");
                String priorityString = scanner.nextLine();
                Priority priority = Priority.valueOf(priorityString.toUpperCase());
                displayTasks(controller.filterTasksByPriority(priority));
                break;
            case 2:
                // Filter by Category
                System.out.println("Enter category (WORK, STUDY, PERSONAL, HEALTH):");
                String categoryString = scanner.nextLine();
                Category category = Category.valueOf(categoryString.toUpperCase());
                displayTasks(controller.filterTasksByCategory(category));
                break;
            case 3:
                // Filter by Due Date
                System.out.println("Enter due date (YYYY-MM-DD):");
                String dateString = scanner.nextLine();
                LocalDate dueDate = LocalDate.parse(dateString);
                displayTasks(controller.filterTasksByDueDate(dueDate));
                break;
            case 4:
                // Filter by Completion Status
                System.out.println("Enter completion status (true for completed, false for not completed):");
                boolean isCompleted = scanner.nextBoolean();
                displayTasks(controller.filterTasksByCompletionStatus(isCompleted));
                scanner.nextLine(); // Consume the remaining newline
                break;
            default:
                System.out.println("Invalid choice. Please choose a valid option.");
                break;
        }
    }
    
    private void sortTasks() {
        System.out.println("Sort by: \n1. Priority \n2. Category \n3. Due Date \n4. Name");
        System.out.print("Your choice: ");
        int sortChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the remaining newline
    
        switch (sortChoice) {
            case 1:
                // Sort by Priority
                displayTasks(controller.sortTasksByPriority());
                break;
            case 2:
                // Sort by Category
                displayTasks(controller.sortTasksByCategory());
                break;
            case 3:
                // Sort by Due Date
                displayTasks(controller.sortTasksByDueDate());
                break;
            case 4:
                // Sort by Name
                displayTasks(controller.sortTasksByName());
                break;
            default:
                System.out.println("Invalid choice. Please choose a valid option.");
                break;
        }
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
        app.start();
    }
}
