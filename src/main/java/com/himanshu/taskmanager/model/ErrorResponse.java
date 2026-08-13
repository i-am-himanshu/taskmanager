package com.himanshu.taskmanager.model;

import java.util.Map;

public class ErrorResponse {

    private int status;

    private String message;

    private Map<String, String> errors;

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public int getStatus() {
        return status;
    }

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(int status, String message, Map<String, String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }
}
