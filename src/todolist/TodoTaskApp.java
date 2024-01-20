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

    public TodoTaskApp() {
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
        // Implementation to add a task
    }

    private void deleteTask() {
        // Implementation to delete a task by ID or name
    }

    private void updateTask() {
        // Implementation to update a task by ID or name
    }

    private void listTasks() {
        // Implementation to list tasks, could include filter and sort options
    }

    public static void main(String[] args) {
        new TodoTaskApp().start();
    }
}
