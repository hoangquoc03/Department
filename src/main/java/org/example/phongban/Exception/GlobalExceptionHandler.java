package org.example.phongban.Exception;


import org.example.phongban.Response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(
            MethodArgumentNotValidException ex
    ) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    errors.put(
                            error.getField(),
                            error.getDefaultMessage()
                    );
                });

        ApiResponse<?> response =
                new ApiResponse<>(
                        "FAIL",
                        "Dữ liệu không hợp lệ",
                        errors
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFoundException(
            ResourceNotFoundException ex
    ) {

        ApiResponse<?> response =
                new ApiResponse<>(
                        "FAIL",
                        ex.getMessage(),
                        null
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<?>> handleDuplicateException(
            DuplicateResourceException ex
    ) {

        ApiResponse<?> response =
                new ApiResponse<>(
                        "FAIL",
                        ex.getMessage(),
                        null
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.CONFLICT
        );
    }
    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidFileException(
            InvalidFileException ex
    ) {

        ApiResponse<?> response =
                new ApiResponse<>(
                        "FAIL",
                        ex.getMessage(),
                        null
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST
        );
    }
}