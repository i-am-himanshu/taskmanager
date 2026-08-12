package com.himanshu.taskmanager.service;

import com.himanshu.taskmanager.exception.TaskNotFoundException;
import com.himanshu.taskmanager.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final List<Task> taskList = new ArrayList<>();

    private Long nextId = 1L;

    public Task createTask(Task task){

        task.setId(nextId);
        taskList.add(task);

        nextId++;

        return task;
    }

    public List<Task> getAllTasks() {
        return taskList;
    }

    public Task getTask(Long id) {
        return taskList.stream()
                .filter(task -> id.equals(task.getId()))
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException("Task not found."));
    }

    public void deleteTask(Long id) {
        boolean isDeleted = taskList.removeIf(task -> id.equals(task.getId()));

        if(!isDeleted){
            throw new TaskNotFoundException("Task not found.");
        }
    }

    public Task updateTask(Long id, Task task) {
        Task foundTask = taskList.stream()
                .filter(currentTask -> id.equals(currentTask.getId()))
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException("Task not found."));

        foundTask.setTitle(task.getTitle());
        foundTask.setDescription(task.getDescription());
        foundTask.setStatus(task.getStatus());
        foundTask.setPriority(task.getPriority());

        return foundTask;
    }
}
