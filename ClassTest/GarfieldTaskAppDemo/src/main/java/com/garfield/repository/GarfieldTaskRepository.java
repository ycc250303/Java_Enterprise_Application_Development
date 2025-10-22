package com.garfield.repository;

import com.garfield.task.TaskException;
import com.garfield.task.Task;
import java.util.List;

// 任务管理抽象接口
public interface GarfieldTaskRepository {
    void save(Task task) throws TaskException;
    Task findById(String id) throws TaskException;
    List<Task> findAll();
    void deleteById(String id) throws TaskException;
    List<Task> findByStatus(Task.Status status);
    List<Task> findByPriority(Task.Priority priority);
    void update(Task task) throws TaskException;
}