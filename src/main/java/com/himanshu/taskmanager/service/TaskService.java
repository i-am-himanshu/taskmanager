package com.himanshu.taskmanager.service;

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
}
