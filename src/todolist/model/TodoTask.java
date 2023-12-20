package todolist.model;

public class TodoTask {
    //Properties of a task
    private String name; //Tasks's name
    private String description; //About the task
    private boolean isCompleted; //Is it completed?

    //Constructor: to set initial values for a task
    public TodoTask(String name, String description){
        this.name = name;
        this.description = description;
        this.isCompleted = false; //Tasks always start off not completed!
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

    //Override toString to print details of a task
    @Override
    public String toString() {
        return "TodoTask{" +
           "name='" + name + '\'' +
           ", description='" + description + '\'' +
           ", isCompleted=" + isCompleted +
           '}';
    }

    //TESTING
    /* To Test in Terminal...
     * 1. Compile: javac src/todolist/model/TodoTask.java
     * 2. Run: java src/todolist/model/TodoTask
     */
    public static void main(String[] args){
        TodoTask task1 = new TodoTask("Study", "Chemistry, Biology, Physics");
        System.out.println(task1);
        TodoTask task2 = new TodoTask("Send email", "Weekly Stand-Up");
        task2.setCompleted(true);
        System.out.println(task2);
    }
}
