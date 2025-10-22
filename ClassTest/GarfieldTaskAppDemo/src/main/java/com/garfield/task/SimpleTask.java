package com.garfield.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

// 普通任务类
public class SimpleTask extends Task{
    public SimpleTask(){};

    public SimpleTask(String id, String title,Priority priority){
        super(id, title, priority);
    }
    @Override
    public String describe() {
        return String.format("[%s] %s (id=%s, priority=%s, created=%s)",
                status, title, id, priority, createdAt.toString());
    }
}
