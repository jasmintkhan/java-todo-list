package todolist.controller;
import java.util.ArrayList;
import todolist.model.TodoTask;

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
    public void deleteTaskByName(TodoTask task, String taskName){
        tasks.removeIf(tasks -> task.getName().equals(taskName));
    }

    //Deletes a task from the list BY ID
    public void deleteTaskByID(TodoTask task, int taskID){
        tasks.removeIf(tasks -> task.getId() == taskID);
    }

    //Updates a task in the list
    public void updateTask(TodoTask task){
        //Nothing to do here yet
    }

    //Filters tasks by priority
}