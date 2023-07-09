package com.mustafakahriman.employeemanagementsystem.controller.exception;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.mustafakahriman.employeemanagementsystem.controller.response.BaseResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String ERROR_MESSAGE = "There is an error";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handleException(Exception ex) {
        BaseResponse response = BaseResponse.builder().errors(List.of(ex.toString())).message(ERROR_MESSAGE)
                .success(false).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<BaseResponse> handleMethodArgNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        BaseResponse response = BaseResponse.builder().errors(errors).message(ERROR_MESSAGE).success(false).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<BaseResponse> handleItemNotFoundException(
            NoSuchElementException ex, WebRequest request) {
        BaseResponse response = BaseResponse.builder().errors(List.of(ERROR_MESSAGE)).message(ERROR_MESSAGE)
                .success(false).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}