package com.himanshu.taskmanager.service;

import com.himanshu.taskmanager.dto.TaskRequest;
import com.himanshu.taskmanager.dto.TaskResponse;
import com.himanshu.taskmanager.exception.TaskNotFoundException;
import com.himanshu.taskmanager.model.Task;
import com.himanshu.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public TaskResponse createTask(TaskRequest taskRequest){

        Task task = new Task();

        task.setTitle(taskRequest.getTitle());
        task.setStatus(taskRequest.getStatus());
        task.setPriority(taskRequest.getPriority());
        task.setDescription(taskRequest.getDescription());

        Task savedTask = taskRepository.save(task);

        return mapToTaskResponse(savedTask);
    }

    private TaskResponse mapToTaskResponse(Task task) {
        TaskResponse taskResponse = new TaskResponse();

        taskResponse.setId(task.getId());
        taskResponse.setTitle(task.getTitle());
        taskResponse.setStatus(task.getStatus());
        taskResponse.setPriority(task.getPriority());
        taskResponse.setDescription(task.getDescription());

        return taskResponse;
    }

    public List<TaskResponse> getAllTasks() {

        return taskRepository.findAll().stream()
                .map(this::mapToTaskResponse)
                .toList();

    }

    public TaskResponse getTask(Long id) {
        Task foundTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found."));

        return mapToTaskResponse(foundTask);
    }

    public void deleteTask(Long id) {
//        boolean isDeleted = taskList.removeIf(task -> id.equals(task.getId()));
//
//        if(!isDeleted){
//            throw new TaskNotFoundException("Task not found.");
//        }

        if(!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task not found.");
        }

        taskRepository.deleteById(id);
    }

    public TaskResponse updateTask(Long id, TaskRequest taskRequest) {
        Task foundTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found."));

        foundTask.setTitle(taskRequest.getTitle());
        foundTask.setDescription(taskRequest.getDescription());
        foundTask.setStatus(taskRequest.getStatus());
        foundTask.setPriority(taskRequest.getPriority());

        taskRepository.save(foundTask);

        return mapToTaskResponse(foundTask);
    }
}
