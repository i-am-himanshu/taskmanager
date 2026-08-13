package com.himanshu.taskmanager.exception;

import com.himanshu.taskmanager.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFound(TaskNotFoundException taskNotFoundException){

        ErrorResponse errorResponse =
                new ErrorResponse(HttpStatus.NOT_FOUND.value(), taskNotFoundException.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception){

        Map<String, String> errorsMap = exception.getBindingResult()
                .getFieldErrors().stream().collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage()
                ));

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"Validation Failed.", errorsMap);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
