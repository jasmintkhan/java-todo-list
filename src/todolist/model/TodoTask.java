package todolist.model;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class TodoTask {
    //Properties of a task
    private String name; //Tasks's name
    private String description; //About the task
    private boolean isCompleted; //Is it completed?
    private LocalDate dueDate; //Due Date for task

    public enum Priority { //fixed constants for priority
        LOW, MEDIUM, HIGH
    }
    private Priority priority; //Priority of task

    public enum Category { //fixed constants for category
        WORK, STUDY, PERSONAL, HEALTH
    }
    private Category category; //Category of task
    
    private static final AtomicInteger count = new AtomicInteger(0); //For unique ID generation
    private int id;

    //Constructor: to set initial values for a task
    public TodoTask(String name, String description, LocalDate dueDate, Priority priority, Category category){
        this.name = name;
        this.description = description;
        this.isCompleted = false; //Tasks always start off not completed!
        this.dueDate = dueDate; //sets the due date
        this.priority = priority; 
        this.category = category; 
        this.id = count.incrementAndGet(); //Assign and increment the unique ID
    }

    //Getters and Setters (existing items)
    //Getters retrieve information
    //Setters update information 
    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority){
        this.priority = priority;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category){
        this.category = category;
    }

    public int getId() {
        return id;
    }

    //Override toString to print details of a task
    @Override
    public String toString() {
        return "TodoTask{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", isCompleted=" + isCompleted +
            ", dueDate=" + dueDate +
            ", priority=" + priority +
            ", category=" + category +
            '}';
    }

    //TESTING
    /* To Test in Terminal...
     * 1. Compile: javac src/todolist/model/TodoTask.java
     * 2. Run: java src/todolist/model/TodoTask
     */
    public static void main(String[] args){
        TodoTask task1 = new TodoTask("Study", "Chemistry, Biology, Physics", LocalDate.of(2023, 12, 31), Priority.HIGH, Category.STUDY);
        System.out.println(task1);
        TodoTask task2 = new TodoTask("Send email", "Weekly Stand-Up", LocalDate.of(2023, 12, 31), Priority.LOW, Category.WORK);
        task2.setCompleted(true);
        System.out.println(task2);
    }
}
