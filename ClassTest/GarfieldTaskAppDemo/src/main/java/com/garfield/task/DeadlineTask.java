package com.garfield.task;

import java.time.LocalDateTime;

// 有ddl的任务类
public class DeadlineTask  extends Task{
    LocalDateTime deadline;

    public DeadlineTask(){}

    public DeadlineTask(String id, String title, Priority priority,LocalDateTime deadline){
        super(id, title, priority);
        this.deadline = deadline;
    }
    @Override
    public String describe() {
        return String.format("[%s] %s (id=%s, priority=%s, created=%s ,deadline=%s)",
                status, title, id, priority, createdAt.toString(),deadline.toString());
    }
}
