package com.himanshu.taskmanager;

import com.himanshu.taskmanager.dto.TaskRequest;
import com.himanshu.taskmanager.dto.TaskResponse;
import com.himanshu.taskmanager.model.TaskPriority;
import com.himanshu.taskmanager.model.TaskStatus;
import com.himanshu.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void testSomething() {

    }

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
                .andExpect(status().isOk());
    }
}
