package com.garfield.task;

import java.util.Objects;
import java.time.LocalDateTime;

// 抽象类 Task
public abstract class Task {
    public enum Status {OPEN, DONE}

    public enum Priority {LOW, MEDIUM, HIGH}

    protected String id;
    protected String title;
    protected LocalDateTime createdAt;
    protected Status status;
    protected Priority priority;

    public Task() {
    }

    public Task(String id, String title, Priority priority) {
        this.id = id;
        this.title = title;
        this.priority = priority == null ? Priority.MEDIUM : priority;
        this.createdAt = LocalDateTime.now();
        this.status = Status.OPEN;
    }

    //Getter and Setter
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Status getStatus() {
        return status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void markDone() {
        this.status = Status.DONE;
    }

    public abstract String describe();
}