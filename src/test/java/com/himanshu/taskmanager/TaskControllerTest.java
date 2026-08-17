package com.himanshu.taskmanager;

import com.himanshu.taskmanager.dto.TaskRequest;
import com.himanshu.taskmanager.dto.TaskResponse;
import com.himanshu.taskmanager.exception.TaskNotFoundException;
import com.himanshu.taskmanager.model.TaskPriority;
import com.himanshu.taskmanager.model.TaskStatus;
import com.himanshu.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@WebMvcTest
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TaskService taskService;

    @Test
    void createTask_shouldReturnCreatedTask() throws Exception {

        TaskResponse taskResponse = new TaskResponse();

        taskResponse.setId(1L);
        taskResponse.setTitle("Learn REST APIs");
        taskResponse.setDescription("Understand GET and POST");
        taskResponse.setStatus(TaskStatus.IN_PROGRESS);
        taskResponse.setPriority(TaskPriority.MEDIUM);

        when(taskService.createTask(any()))
                .thenReturn(taskResponse);

        TaskRequest taskRequest = new TaskRequest();

        taskRequest.setTitle("Learn REST APIs");
        taskRequest.setDescription("Understand GET and POST");
        taskRequest.setStatus(TaskStatus.IN_PROGRESS);
        taskRequest.setPriority(TaskPriority.MEDIUM);

        String requestJson = objectMapper.writeValueAsString(taskRequest);

        mockMvc.perform(
                        post("/tasks")
                                .contentType("application/json")
                                .content(requestJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Learn REST APIs"))
                .andExpect(jsonPath("$.description").value("Understand GET and POST"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
                .andExpect(jsonPath("$.priority").value("MEDIUM"));
    }

    @Test
    void createTask_withInvalidRequest_shouldReturnBadRequest() throws Exception {

        TaskRequest taskRequest = new TaskRequest();

        taskRequest.setTitle("");
        taskRequest.setDescription("Understand GET and POST");
        taskRequest.setStatus(null);
        taskRequest.setPriority(TaskPriority.MEDIUM);

        String requestJson = objectMapper.writeValueAsString(taskRequest);

        mockMvc.perform(
                        post("/tasks")
                                .contentType("application/json")
                                .content(requestJson)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation Failed."))
                .andExpect(jsonPath("$.errors.title").value("Title is required."))
                .andExpect(jsonPath("$.errors.status").value("Status is required."));
    }

    @Test
    void getTask_shouldReturnTask() throws Exception {

        TaskResponse taskResponse = new TaskResponse();

        taskResponse.setId(1L);
        taskResponse.setTitle("Learn REST APIs");
        taskResponse.setDescription("Understand GET and POST");
        taskResponse.setStatus(TaskStatus.IN_PROGRESS);
        taskResponse.setPriority(TaskPriority.MEDIUM);

        when(taskService.getTask(1L))
                .thenReturn(taskResponse);

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Learn REST APIs"))
                .andExpect(jsonPath("$.description").value("Understand GET and POST"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
                .andExpect(jsonPath("$.priority").value("MEDIUM"));
    }


    @Test
    void getTask_whenTaskDoesNotExist_shouldReturnNotFound() throws Exception {

        when(taskService.getTask(999L))
                .thenThrow(new TaskNotFoundException("Task not found."));

        mockMvc.perform(get("/tasks/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Task not found."));
    }

    @Test
    void deleteTask_shouldReturnOk() throws Exception {

        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isOk());
    }

    @Test
    void updateTask_shouldReturnUpdatedTask() throws Exception {

        TaskResponse taskResponse = new TaskResponse();

        taskResponse.setId(1L);
        taskResponse.setTitle("Learn PUT");
        taskResponse.setDescription("Understand PUT API");
        taskResponse.setStatus(TaskStatus.IN_PROGRESS);
        taskResponse.setPriority(TaskPriority.HIGH);

        when(taskService.updateTask(eq(1L), any(TaskRequest.class)))
                .thenReturn(taskResponse);

        TaskRequest taskRequest = new TaskRequest();

        taskRequest.setTitle("Learn PUT");
        taskRequest.setDescription("Understand PUT API");
        taskRequest.setStatus(TaskStatus.IN_PROGRESS);
        taskRequest.setPriority(TaskPriority.HIGH);

        String requestJson = objectMapper.writeValueAsString(taskRequest);

        mockMvc.perform(
                        put("/tasks/1")
                                .contentType("application/json")
                                .content(requestJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Learn PUT"))
                .andExpect(jsonPath("$.description").value("Understand PUT API"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
                .andExpect(jsonPath("$.priority").value("HIGH"));
    }
}
