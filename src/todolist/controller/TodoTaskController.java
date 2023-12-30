package todolist.controller;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import todolist.model.TodoTask;
import todolist.model.TodoTask.Priority; // Importing Priority
import todolist.model.TodoTask.Category; // Importing Category

// Adds new tasks
// Deletes tasks
// Updates tasks
// Filters/sorts tasks by priority, category, due date, etc.
public class TodoTaskController {
    private ArrayList<TodoTask> tasks = new ArrayList<TodoTask>(); //Stores TodoTask objects

    //Constructor: to set initial values for a task
    public TodoTaskController(){
        //Nothing to do here yet
    }

    //Adds a new task to the list
    public void addTask(TodoTask task){
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        tasks.add(task);
    }

    //Deletes a task from the list BY NAME
    public void deleteTaskByName(String taskName){
        // Check if any task matches the provided name
        boolean exists = tasks.stream().anyMatch(task -> task.getName().equals(taskName));
        if (!exists) {
            throw new IllegalArgumentException("No task found with name: " + taskName);
        }
        tasks.removeIf(tasks -> tasks.getName().equals(taskName));
    }

    //Deletes a task from the list BY ID
    public void deleteTaskByID(int taskID){
        // Check if any task matches the provided ID
        boolean exists = tasks.stream().anyMatch(task -> task.getId() == taskID);
        if (!exists) {
            throw new IllegalArgumentException("No task found with ID: " + taskID);
        }
        tasks.removeIf(tasks -> tasks.getId() == taskID);
    }

    //Updates a task in the list BY NAME
    public void updateTaskByName(String taskName, String newName, String newDescription, LocalDate newDueDate, boolean newIsCompleted, Priority newPriority, Category newCategory){
        // Check if the task with the given name exists
        boolean exists = tasks.stream().anyMatch(task -> task.getName().equals(taskName));
        if (!exists) {
            throw new IllegalArgumentException("No task found with name: " + taskName);
        }
        // Validate newName
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Task name cannot be null or empty.");
        }
        // Validate newDescription
        if (newDescription == null) {
            throw new IllegalArgumentException("Task description cannot be null.");
        }
        // Validate newDueDate
        if (newDueDate != null && newDueDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date cannot be in the past.");
        }
        // Validate newPriority
        if (newPriority == null) {
            throw new IllegalArgumentException("Priority cannot be null.");
        }
        // Validate newCategory
        if (newCategory == null) {
            throw new IllegalArgumentException("Category cannot be null.");
        }

        for (TodoTask task : tasks) {
            if (task.getName().equals(taskName)) {
                task.setName(newName);
                task.setDescription(newDescription);
                task.setDueDate(newDueDate);
                task.setCompleted(newIsCompleted);
                task.setPriority(newPriority);
                task.setCategory(newCategory);
                break; //Stop the loop once the task is found and updated
            }
        }
    }

    //Updates a task in the list BY ID
    public void updateTaskByID(int taskID, String newName, String newDescription, LocalDate newDueDate, boolean newIsCompleted, Priority newPriority, Category newCategory){
        // Check if the task with the given ID exists
        boolean exists = tasks.stream().anyMatch(task -> task.getId() == taskID);
        if (!exists) {
            throw new IllegalArgumentException("No task found with ID: " + taskID);
        }
        // Validate newName
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Task name cannot be null or empty.");
        }
        // Validate newDescription
        if (newDescription == null) {
            throw new IllegalArgumentException("Task description cannot be null.");
        }
        // Validate newDueDate
        if (newDueDate != null && newDueDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date cannot be in the past.");
        }
        // Validate newPriority
        if (newPriority == null) {
            throw new IllegalArgumentException("Priority cannot be null.");
        }
        // Validate newCategory
        if (newCategory == null) {
            throw new IllegalArgumentException("Category cannot be null.");
        }

        for (TodoTask task : tasks) {
            if (task.getId() == taskID) {
                task.setName(newName);
                task.setDescription(newDescription);
                task.setDueDate(newDueDate);
                task.setCompleted(newIsCompleted);
                task.setPriority(newPriority);
                task.setCategory(newCategory);
                break; //Stop the loop once the task is found and updated
            }
        }
    }

    //Filters tasks by priority
    public List<TodoTask> filterTasksByPriority(Priority priority) {
        if (priority == null) {
            throw new IllegalArgumentException("Priority cannot be null.");
        }
        return tasks.stream()
                    .filter(task -> task.getPriority() == priority)
                    .collect(Collectors.toList());
    }

    //Filters tasks by category
    public List<TodoTask> filterTasksByCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null.");
        }
        return tasks.stream()
                    .filter(task -> task.getCategory() == category)
                    .collect(Collectors.toList());
    }

    //Filters tasks by due date
    public List<TodoTask> filterTasksByDueDate(LocalDate dueDate) {
        if (dueDate == null) {
            throw new IllegalArgumentException("Due date cannot be null.");
        }
        return tasks.stream()
                    .filter(task -> task.getDueDate().equals(dueDate))
                    .collect(Collectors.toList());
    }

    //Filters tasks by completion status
    public List<TodoTask> filterTasksByCompletionStatus(boolean isCompleted) {
        return tasks.stream()
                    .filter(task -> task.isCompleted() == isCompleted)
                    .collect(Collectors.toList());
    }

    //Sorts tasks by priority
    public List<TodoTask> sortTasksByPriority() {
        return tasks.stream()
                    .sorted(Comparator.comparing(TodoTask::getPriority, Comparator.nullsLast(Enum::compareTo)))
                    .collect(Collectors.toList());
    }
    
    //Sorts tasks by category
    public List<TodoTask> sortTasksByCategory() {
        return tasks.stream()
                    .sorted(Comparator.comparing(TodoTask::getCategory, Comparator.nullsLast(Enum::compareTo)))
                    .collect(Collectors.toList());
    }

    //Sorts tasks by due date
    public List<TodoTask> sortTasksByDueDate() {
        return tasks.stream()
                    .sorted(Comparator.comparing(TodoTask::getDueDate, Comparator.nullsLast(LocalDate::compareTo)))
                    .collect(Collectors.toList());
    }

    //Sorts tasks by name
    public List<TodoTask> sortTasksByName() {
        return tasks.stream()
                    .sorted(Comparator.comparing(TodoTask::getName, Comparator.nullsLast(String::compareTo)))
                    .collect(Collectors.toList());
    }


    /*public static void main(String[] args) {
        //Create the controller instance
        TodoTaskController controller = new TodoTaskController();
        
        //Create some tasks
        TodoTask task1 = new TodoTask("Study", "Study for exams", LocalDate.now().plusDays(1), Priority.HIGH, Category.STUDY);
        TodoTask task2 = new TodoTask("Groceries", "Buy milk and bread", LocalDate.now().plusDays(2), Priority.MEDIUM, Category.PERSONAL);
        TodoTask task3 = new TodoTask("Gym", "Leg day", LocalDate.now().plusDays(2), Priority.LOW, Category.HEALTH);
        TodoTask task4 = new TodoTask("Work", "Weekly Stand-Up", LocalDate.now().plusDays(3), Priority.HIGH, Category.WORK);
        TodoTask task5 = new TodoTask("Clean", "Clean Kitchen", LocalDate.now().plusDays(3), Priority.MEDIUM, Category.PERSONAL);
        TodoTask task6 = new TodoTask("Laundry", "Wash clothes", LocalDate.now().plusDays(4), Priority.LOW, Category.PERSONAL);
        //Add tasks to the controller
        controller.addTask(task1);
        controller.addTask(task2);
        controller.addTask(task3);
        controller.addTask(task4);
        controller.addTask(task5);
        controller.addTask(task6);


        //Testing filtering and sorting
        List<TodoTask> highPriorityTasks = controller.filterTasksByPriority(Priority.HIGH);
        System.out.println("High Priority Tasks: " + highPriorityTasks);

        List<TodoTask> personalTasks = controller.filterTasksByCategory(Category.PERSONAL);
        System.out.println("Personal Tasks: " + personalTasks);

        List<TodoTask> tasksDueToday = controller.filterTasksByDueDate(LocalDate.now());
        System.out.println("Tasks due today: " + tasksDueToday);

        List<TodoTask> tasksDueTomorrow = controller.filterTasksByDueDate(LocalDate.now().plusDays(1));
        System.out.println("Tasks due tomorrow: " + tasksDueTomorrow);

        List<TodoTask> tasksSortedByPriority = controller.sortTasksByPriority();
        System.out.println("\nTasks sorted by priority: " + tasksSortedByPriority);

        List<TodoTask> tasksSortedByCategory = controller.sortTasksByCategory();
        System.out.println("\nTasks sorted by category: " + tasksSortedByCategory);

        List<TodoTask> tasksSortedByDueDate = controller.sortTasksByDueDate();
        System.out.println("\nTasks sorted by due date: " + tasksSortedByDueDate);

        //List<TodoTask> noPriorityTasks = controller.filterTasksByPriority(null);  // This should throw an exception

        /////////////////////////////

        // Print all tasks to verify they were added
        System.out.println("Tasks after addition:");
        for (TodoTask task : controller.tasks) {
            System.out.println(task);
        }
    
        // Delete a task by name
        //controller.deleteTaskByName("Study");

        // Delete a task by ID
        //controller.deleteTaskByID(2); // Assuming task2 has ID 2

        

        // Print all tasks to verify deletion
        System.out.println("\nTasks after deletion:");
        for (TodoTask task : controller.tasks) {
            System.out.println(task);
        }

        // Update a task by name
        controller.updateTaskByName("Groceries", "Supermarket", "Buy eggs and cheese", LocalDate.now().plusDays(3), true, Priority.LOW, Category.PERSONAL);

        // Update a task by ID
        controller.updateTaskByID(1, "Study Harder", "Study for math exam", LocalDate.now().plusDays(1), false, Priority.HIGH, Category.STUDY); // Assuming task1 has ID 1

        // Print all tasks to verify updates
        System.out.println("\nTasks after updates:");
        for (TodoTask task : controller.tasks) {
            System.out.println(task);
        }

        // edge cases
        //controller.addTask(null); // Should throw an exception. GOOD
        //controller.deleteTaskByName("fakeName"); // Should throw an exception GOOD
        //controller.deleteTaskByID(-1); // Should throw an exception. GOOD
        //controller.updateTaskByName("Supermarket", "TestingErrors", null, null, false, null, null); // Should throw an exception
        //controller.updateTaskByID(-1, null, null, null, false, null, null); // Should throw an exception


    }*/
    
}