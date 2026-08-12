package com.himanshu.taskmanager.controller;

import com.himanshu.taskmanager.dto.TaskRequest;
import com.himanshu.taskmanager.dto.TaskResponse;
import com.himanshu.taskmanager.model.Task;
import com.himanshu.taskmanager.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/tasks")
    public TaskResponse createTask(@RequestBody TaskRequest taskRequest) {

        return taskService.createTask(taskRequest);

    }

    @GetMapping("/tasks")
    public List<TaskResponse> getAllTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping("/tasks/{id}")
    public TaskResponse getTask(@PathVariable Long id){
        return taskService.getTask(id);
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
    }

    @PutMapping("/tasks/{id}")
    public TaskResponse updateTask(@PathVariable Long id, @RequestBody TaskRequest taskRequest) {
        return taskService.updateTask(id, taskRequest);
    }
}
