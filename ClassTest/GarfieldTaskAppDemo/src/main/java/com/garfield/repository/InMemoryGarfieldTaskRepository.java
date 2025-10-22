package com.garfield.repository;

import com.garfield.task.Task;
import com.garfield.task.TaskException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 任务管理类
public class InMemoryGarfieldTaskRepository implements GarfieldTaskRepository{
    Map<String, Task> taskMap = new ConcurrentHashMap<>();
    @Override
    public void save(Task task) throws TaskException{
        if(task == null){
            throw new TaskException("Task cannot be null");
        }
        if(task.getId() == null){
            throw new TaskException("Task id cannot be null");
        }
        if(task.getTitle()==null){
            throw new TaskException("Task title cannot be null");
        }
        taskMap.put(task.getId(),task);
    }

    @Override
    public Task findById(String id) throws TaskException{
        if(id == null){
            throw new TaskException("Task id cannot be null");
        }
        for(Task task: taskMap.values()){
            if(task.getId().equals(id)){
                return task;
            }
        }
        throw new TaskException("Task not found");
    }

    @Override
    public List<Task> findAll() {
        return taskMap.values().stream().toList();
    }

    @Override
    public void deleteById(String id) throws TaskException{
        if(id == null){
            throw new TaskException("Task id cannot be null");
        }
        if(taskMap.containsKey(id)){
            taskMap.remove(id);
        }
        else {
            throw new TaskException("Task not found");
        }
    }

    @Override
    public List<Task> findByStatus(Task.Status status) {
        return taskMap.values().stream().filter(task -> task.getStatus() == status).toList();
    }

    @Override
    public List<Task> findByPriority(Task.Priority priority) {
        return taskMap.values().stream().filter(task -> task.getPriority() == priority).toList();
    }

    @Override
    public void update(Task task) throws TaskException{
        if(task == null){
            throw new TaskException("Task cannot be null");
        }
        if(taskMap.containsKey(task.getId())){
            taskMap.put(task.getId(),task);
        }
        else{
            throw new TaskException("Task not found");
        }
    }
}
