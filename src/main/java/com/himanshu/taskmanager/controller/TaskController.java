package com.himanshu.taskmanager.controller;

import com.himanshu.taskmanager.model.Task;
import com.himanshu.taskmanager.service.TaskService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/tasks")
    public Task createTask(@RequestBody Task task) {

        taskService.createTask(task);

        return task;
    }
}
