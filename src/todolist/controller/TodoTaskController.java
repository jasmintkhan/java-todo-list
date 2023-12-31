package todolist.controller;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.*;
import java.util.logging.Logger;


import todolist.model.TodoTask;
import todolist.model.TodoTask.Priority; // Importing Priority
import todolist.model.TodoTask.Category; // Importing Category

// Adds new tasks
// Deletes tasks
// Updates tasks
// Filters/sorts tasks by priority, category, due date, etc.
public class TodoTaskController {
    private static final Logger LOGGER = Logger.getLogger(TodoTaskController.class.getName()); // Logger instance
    private ArrayList<TodoTask> tasks = new ArrayList<TodoTask>(); //Stores TodoTask objects

    //Constructor: to set initial values for a task
    public TodoTaskController(){
        //Nothing to do here yet
    }

    //Adds a new task to the list
    public void addTask(TodoTask task){
        if (task == null) {
            LOGGER.severe("Attempted to add a null task.");
            throw new IllegalArgumentException("Task cannot be null");
        }
        tasks.add(task);
        LOGGER.info("Added a new task: " + task.getName());
    }

    //Deletes a task from the list BY NAME
    public void deleteTaskByName(String taskName){
        LOGGER.info("Attempting to delete task with name: " + taskName);
        // Check if any task matches the provided name
        boolean exists = tasks.stream().anyMatch(task -> task.getName().equals(taskName));
        if (!exists) {
            LOGGER.warning("No task found with name: " + taskName);
            throw new IllegalArgumentException("No task found with name: " + taskName);
        }
        tasks.removeIf(tasks -> tasks.getName().equals(taskName));
    }

    //Deletes a task from the list BY ID
    public void deleteTaskByID(int taskID){
        LOGGER.info("Attempting to delete task with ID: " + taskID);
        // Check if any task matches the provided ID
        boolean exists = tasks.stream().anyMatch(task -> task.getId() == taskID);
        if (!exists) {
            LOGGER.warning("No task found with ID: " + taskID);
            throw new IllegalArgumentException("No task found with ID: " + taskID);
        }
        tasks.removeIf(tasks -> tasks.getId() == taskID);
    }

    //Updates a task in the list BY NAME
    public void updateTaskByName(String taskName, String newName, String newDescription, LocalDate newDueDate, boolean newIsCompleted, Priority newPriority, Category newCategory){
        LOGGER.info("Attempting to update task: " + taskName);
        // Check if the task with the given name exists
        boolean exists = tasks.stream().anyMatch(task -> task.getName().equals(taskName));
        if (!exists) {
            LOGGER.warning("Update failed: No task found with name: " + taskName);
            throw new IllegalArgumentException("No task found with name: " + taskName);
        }
        // Validate newName
        if (newName == null || newName.trim().isEmpty()) {
            LOGGER.severe("Attempted to have a null or empty task name.");
            throw new IllegalArgumentException("Task name cannot be null or empty.");
        }
        // Validate newDescription
        if (newDescription == null) {
            LOGGER.severe("Attempted to have a null or empty task description.");
            throw new IllegalArgumentException("Task description cannot be null.");
        }
        // Validate newDueDate
        if (newDueDate != null && newDueDate.isBefore(LocalDate.now())) {
            LOGGER.severe("Attempted to have a due date in the past.");
            throw new IllegalArgumentException("Due date cannot be in the past.");
        }
        // Validate newPriority
        if (newPriority == null) {
            LOGGER.severe("Attempted to have a null task priority.");
            throw new IllegalArgumentException("Priority cannot be null.");
        }
        // Validate newCategory
        if (newCategory == null) {
            LOGGER.severe("Attempted to have a null task category.");
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
                LOGGER.info("Task updated successfully: " + taskName + " to " + newName);
                break; //Stop the loop once the task is found and updated
            }
        }
    }

    //Updates a task in the list BY ID
    public void updateTaskByID(int taskID, String newName, String newDescription, LocalDate newDueDate, boolean newIsCompleted, Priority newPriority, Category newCategory){
        LOGGER.info("Attempting to update task: " + taskID);
        // Check if the task with the given ID exists
        boolean exists = tasks.stream().anyMatch(task -> task.getId() == taskID);
        if (!exists) {
            LOGGER.warning("Update failed: No task found with name: " + taskID);
            throw new IllegalArgumentException("No task found with ID: " + taskID);
        }
        // Validate newName
        if (newName == null || newName.trim().isEmpty()) {
            LOGGER.severe("Attempted to have a null or empty task name.");
            throw new IllegalArgumentException("Task name cannot be null or empty.");
        }
        // Validate newDescription
        if (newDescription == null) {
            LOGGER.severe("Attempted to have a null or empty task description.");
            throw new IllegalArgumentException("Task description cannot be null.");
        }
        // Validate newDueDate
        if (newDueDate != null && newDueDate.isBefore(LocalDate.now())) {
            LOGGER.severe("Attempted to have a due date in the past.");
            throw new IllegalArgumentException("Due date cannot be in the past.");
        }
        // Validate newPriority
        if (newPriority == null) {
            LOGGER.severe("Attempted to have a null task priority.");
            throw new IllegalArgumentException("Priority cannot be null.");
        }
        // Validate newCategory
        if (newCategory == null) {
            LOGGER.severe("Attempted to have a null task category.");
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
                LOGGER.info("Task updated successfully: Using task ID[" + taskID + "] to change to " + newName);
                break; //Stop the loop once the task is found and updated
            }
        }
    }

    //Filters tasks by priority
    public List<TodoTask> filterTasksByPriority(Priority priority) {
        LOGGER.info("Filtering tasks by priority: " + priority);
        if (priority == null) {
            LOGGER.severe("Attempted to filter by a null task priority.");
            throw new IllegalArgumentException("Priority cannot be null.");
        }
        List<TodoTask> filteredTasks = tasks.stream()
                    .filter(task -> task.getPriority() == priority)
                    .collect(Collectors.toList());
        LOGGER.info("Number of tasks found: " + filteredTasks.size());
        return filteredTasks;
    }

    //Filters tasks by category
    public List<TodoTask> filterTasksByCategory(Category category) {
        LOGGER.info("Filtering tasks by category: " + category);
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null.");
        }
        List<TodoTask> filteredTasks =  tasks.stream()
                    .filter(task -> task.getCategory() == category)
                    .collect(Collectors.toList());
        LOGGER.info("Number of tasks found: " + filteredTasks.size());
        return filteredTasks;
    }

    //Filters tasks by due date
    public List<TodoTask> filterTasksByDueDate(LocalDate dueDate) {
        LOGGER.info("Filtering tasks by due date: " + dueDate);
        if (dueDate == null) {
            throw new IllegalArgumentException("Due date cannot be null.");
        }
        List<TodoTask> filteredTasks =  tasks.stream()
                    .filter(task -> task.getDueDate().equals(dueDate))
                    .collect(Collectors.toList());
        LOGGER.info("Number of tasks found: " + filteredTasks.size());
        return filteredTasks;
    }

    //Filters tasks by completion status
    public List<TodoTask> filterTasksByCompletionStatus(boolean isCompleted) {
        LOGGER.info("Filtering tasks by completion status: " + isCompleted);
        List<TodoTask> filteredTasks =  tasks.stream()
                    .filter(task -> task.isCompleted() == isCompleted)
                    .collect(Collectors.toList());
        LOGGER.info("Number of tasks found: " + filteredTasks.size());    
        return filteredTasks;
    }

    //Sorts tasks by priority
    public List<TodoTask> sortTasksByPriority() {
        LOGGER.info("Sorting tasks by priority.");
        List<TodoTask> sortedTasks = tasks.stream()
                    .sorted(Comparator.comparing(TodoTask::getPriority, Comparator.nullsLast(Enum::compareTo)))
                    .collect(Collectors.toList());
        LOGGER.info("Sorting completed.");
        return sortedTasks;
    }
    
    //Sorts tasks by category
    public List<TodoTask> sortTasksByCategory() {
        LOGGER.info("Sorting tasks by category.");
        List<TodoTask> sortedTasks = tasks.stream()
                    .sorted(Comparator.comparing(TodoTask::getCategory, Comparator.nullsLast(Enum::compareTo)))
                    .collect(Collectors.toList());
        LOGGER.info("Sorting completed.");
        return sortedTasks;
    }

    //Sorts tasks by due date
    public List<TodoTask> sortTasksByDueDate() {
        LOGGER.info("Sorting tasks by due date.");
        List<TodoTask> sortedTasks = tasks.stream()
                    .sorted(Comparator.comparing(TodoTask::getDueDate, Comparator.nullsLast(LocalDate::compareTo)))
                    .collect(Collectors.toList());
        LOGGER.info("Sorting completed.");
        return sortedTasks;
    }

    //Sorts tasks by name
    public List<TodoTask> sortTasksByName() {
        LOGGER.info("Sorting tasks by name.");
        List<TodoTask> sortedTasks = tasks.stream()
                    .sorted(Comparator.comparing(TodoTask::getName, Comparator.nullsLast(String::compareTo)))
                    .collect(Collectors.toList());
        LOGGER.info("Sorting completed.");
        return sortedTasks;
    }

    //Searches tasks by name
    public List<TodoTask> searchTasksByName(String query) {
        LOGGER.info("Searching for tasks with name containing: " + query);
        if (query == null || query.trim().isEmpty()) {
            LOGGER.severe("Attempted to search for a null or empty task name.");
            throw new IllegalArgumentException("Search query cannot be null or empty.");
        }
    
        String lowerCaseQuery = query.toLowerCase();
        List<TodoTask> searchResults =  tasks.stream()
                    .filter(task -> task.getName().toLowerCase().contains(lowerCaseQuery))
                    .collect(Collectors.toList());
        LOGGER.info("Number of tasks found: " + searchResults.size());
        return searchResults;
    }
    
    //Searches tasks by id
    public TodoTask searchTaskById(int taskId) {
        LOGGER.info("Searching for task with ID: " + taskId);
        Optional<TodoTask> searchResults = tasks.stream()
                    .filter(task -> task.getId() == taskId)
                    .findFirst();
                    //.orElse(null); // checks if the task is null
        if (searchResults.isPresent()) {
            LOGGER.info("Task found with ID: " + taskId);
            return searchResults.get();
        } 
        else {
            LOGGER.warning("No task found with ID: " + taskId);
            throw new IllegalArgumentException("No task found with ID: " + taskId);
        }
    }
    
    //Periodically checks for upcoming tasks and sends notifications
    public static class NotificationService {
        private TodoTaskController controller;
        private Timer timer;

        public NotificationService(TodoTaskController controller) {
            this.controller = controller;
            this.timer = new Timer();
        }

        public void startChecking() {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    checkForUpcomingTasks();
                    checkForOverdueTasks();
                }
            };

            // Schedule the task to run every hour
            timer.schedule(task, 0, 3600000);
        }

        private void checkForUpcomingTasks() {
            LOGGER.info("Checking for upcoming tasks.");
            LocalDate tomorrow = LocalDate.now().plusDays(1);
            List<TodoTask> upcomingTasks = controller.filterTasksByDueDate(tomorrow);

            for (TodoTask task : upcomingTasks) {
                LOGGER.info("Sending upcoming notification for task: " + task.getName());
                //notification logic 
                System.out.println("UPCOMING: The task '" + task.getName() + "' is due soon!");
            }
        }

        private void checkForOverdueTasks() {
            LOGGER.info("Checking for overdue tasks.");
            LocalDate today = LocalDate.now();
            List<TodoTask> overdueTasks = controller.tasks.stream()
                .filter(task -> (task.getDueDate() != null && task.getDueDate().isBefore(today)) && !task.isCompleted())
                .collect(Collectors.toList());
        
            for (TodoTask task : overdueTasks) {
                LOGGER.info("Sending overdue notification for task: " + task.getName());
                // Implement your notification logic here
                System.out.println("OVERDUE: The task '" + task.getName() + "' was due on " + task.getDueDate() + " and is not completed!");
            }
        }
        
    }


    /*public static void main(String[] args) {
        //Create the controller instance
        TodoTaskController controller = new TodoTaskController();
        
        //Create some tasks
        TodoTask task1 = new TodoTask("Study Java", "Study for exams", LocalDate.now().plusDays(1), Priority.HIGH, Category.STUDY);
        TodoTask task2 = new TodoTask("Groceries", "Buy milk and bread", LocalDate.now().plusDays(2), Priority.MEDIUM, Category.PERSONAL);
        TodoTask task3 = new TodoTask("Gym", "Leg day", LocalDate.now().plusDays(2), Priority.LOW, Category.HEALTH);
        TodoTask task4 = new TodoTask("Work", "Weekly Stand-Up", LocalDate.now().plusDays(3), Priority.HIGH, Category.WORK);
        TodoTask task5 = new TodoTask("Laundry", "Wash clothes", LocalDate.now().plusDays(4), Priority.LOW, Category.PERSONAL);
        TodoTask task6 = new TodoTask("Overdue Task", "This task is overdue", LocalDate.now().minusDays(1), Priority.HIGH, Category.WORK);
        
        //Add tasks to the controller
        controller.addTask(task1);
        controller.addTask(task2);
        controller.addTask(task3);
        controller.addTask(task4);
        controller.addTask(task5);
        controller.addTask(task6);

        // Test the notification service
        NotificationService notificationService = new NotificationService(controller);
        notificationService.startChecking();  // This should trigger your reminders

        // Test searching for an existing task by ID
        int existingId = 1;  // Assuming a task with this ID exists
        TodoTask task = controller.searchTaskById(existingId);
        if (task != null) {
            System.out.println("Found task with ID " + existingId + ": " + task.getName());
        } else {
            System.out.println("No task found with ID " + existingId);
        }

        // Test searching for a non-existing task by ID
        int nonExistingId = 999;  // Assuming no task with this ID exists
        task = controller.searchTaskById(nonExistingId);
        if (task != null) {
            System.out.println("Found task with ID " + nonExistingId + ": " + task.getName());
        } else {
            System.out.println("No task found with ID " + nonExistingId);
        }

        // Test searching for an existing task by name
        String existingName = "Study Java";  // Assuming a task with this name exists
        List<TodoTask> tasks = controller.searchTasksByName(existingName);
        if (!tasks.isEmpty()) {
            System.out.println("Found tasks with name '" + existingName + "':");
            tasks.forEach(foundTask -> System.out.println(foundTask.getName() + " - " + foundTask.getDescription()));
        } else {
            System.out.println("No tasks found with name '" + existingName + "'");
        }

        // Test searching for a non-existing task by name
        String nonExistingName = "Fly to the moon";  // Assuming no task with this name exists
        tasks = controller.searchTasksByName(nonExistingName);
        if (!tasks.isEmpty()) {
            System.out.println("Found tasks with name '" + nonExistingName + "':");
            tasks.forEach(foundTask -> System.out.println(foundTask.getName() + " - " + foundTask.getDescription()));
        } else {
            System.out.println("No tasks found with name '" + nonExistingName + "'");
        }

        // Test searching with a partial name match
        String partialName = "Study";  // Assuming some tasks partially match this name
        tasks = controller.searchTasksByName(partialName);
        if (!tasks.isEmpty()) {
            System.out.println("Found tasks with partial name '" + partialName + "':");
            tasks.forEach(foundTask -> System.out.println(foundTask.getName() + " - " + foundTask.getDescription()));
        } else {
            System.out.println("No tasks found with partial name '" + partialName + "'");
        }


        //Testing filtering and sorting
        //List<TodoTask> highPriorityTasks = controller.filterTasksByPriority(Priority.HIGH);
        //System.out.println("High Priority Tasks: " + highPriorityTasks);

        //List<TodoTask> personalTasks = controller.filterTasksByCategory(Category.PERSONAL);
        //System.out.println("Personal Tasks: " + personalTasks);

        //List<TodoTask> tasksDueToday = controller.filterTasksByDueDate(LocalDate.now());
        //System.out.println("Tasks due today: " + tasksDueToday);

        //List<TodoTask> tasksDueTomorrow = controller.filterTasksByDueDate(LocalDate.now().plusDays(1));
        //System.out.println("Tasks due tomorrow: " + tasksDueTomorrow);

        //List<TodoTask> tasksSortedByPriority = controller.sortTasksByPriority();
        //System.out.println("\nTasks sorted by priority: " + tasksSortedByPriority);

        //List<TodoTask> tasksSortedByCategory = controller.sortTasksByCategory();
        //System.out.println("\nTasks sorted by category: " + tasksSortedByCategory);

        //List<TodoTask> tasksSortedByDueDate = controller.sortTasksByDueDate();
        //System.out.println("\nTasks sorted by due date: " + tasksSortedByDueDate);

        //List<TodoTask> noPriorityTasks = controller.filterTasksByPriority(null);  // This should throw an exception

        /////////////////////////////

        // Print all tasks to verify they were added
        //System.out.println("Tasks after addition:");
        //for (TodoTask task : controller.tasks) {
        //    System.out.println(task);
        //}
    
        // Delete a task by name
        //controller.deleteTaskByName("Study");

        // Delete a task by ID
        //controller.deleteTaskByID(2); // Assuming task2 has ID 2

        

        // Print all tasks to verify deletion
        //System.out.println("\nTasks after deletion:");
        //for (TodoTask task : controller.tasks) {
        //    System.out.println(task);
        //}

        // Update a task by name
        //controller.updateTaskByName("Groceries", "Supermarket", "Buy eggs and cheese", LocalDate.now().plusDays(3), true, Priority.LOW, Category.PERSONAL);

        // Update a task by ID
        //controller.updateTaskByID(1, "Study Harder", "Study for math exam", LocalDate.now().plusDays(1), false, Priority.HIGH, Category.STUDY); // Assuming task1 has ID 1

        // Print all tasks to verify updates
        //System.out.println("\nTasks after updates:");
        //for (TodoTask task : controller.tasks) {
        //    System.out.println(task);
        //}

        // edge cases
        //controller.addTask(null); // Should throw an exception. GOOD
        //controller.deleteTaskByName("fakeName"); // Should throw an exception GOOD
        //controller.deleteTaskByID(-1); // Should throw an exception. GOOD
        //controller.updateTaskByName("Supermarket", "TestingErrors", null, null, false, null, null); // Should throw an exception
        //controller.updateTaskByID(-1, null, null, null, false, null, null); // Should throw an exception


    }*/
    
}