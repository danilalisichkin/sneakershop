package com.sneakershop.SneakerShop.controllers.api;

import com.sneakershop.SneakerShop.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionMessage(e.getMessage(), "resource not found"));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionMessage(e.getMessage(), "bad request"));
    }

    @ExceptionHandler(IllegalOperationException.class)
    public ResponseEntity<Object> handleInsufficientQuantityException(IllegalOperationException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionMessage(e.getMessage(), "illegal operation"));
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<Object> handleInsufficientStockException(InsufficientStockException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionMessage(e.getMessage(), "stock insufficient"));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleNoValidException(MethodArgumentNotValidException e) {
        StringBuilder message = new StringBuilder();
        e.getBindingResult().getAllErrors().forEach((error) ->
                message.append(error.getDefaultMessage())
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionMessage(message.toString(), "validation error"));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleOtherException(Throwable e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionMessage(e.getMessage(), "internal server error"));
    }
}
