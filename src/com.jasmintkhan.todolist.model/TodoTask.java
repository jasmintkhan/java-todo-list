package com.jasmintkhan.todolist.model;

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

    public void String setName(){
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void String setDescription() {
        this.description = description;
    }

    public Boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
