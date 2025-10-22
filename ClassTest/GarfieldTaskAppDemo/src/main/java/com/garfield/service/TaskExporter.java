package com.garfield.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garfield.task.Task;

import java.io.File;
import java.io.IOException;
import java.util.List;

// 任务导出类
public class TaskExporter {
    private final ObjectMapper mapper;

    public TaskExporter(){
        mapper = new ObjectMapper();
    }
    public void exportTasks(List<Task> tasks, String filename) throws IOException{
        mapper.writeValue(new File(filename),tasks);
    }
}
