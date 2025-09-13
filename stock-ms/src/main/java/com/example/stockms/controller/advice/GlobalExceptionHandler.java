package com.example.stockms.config;

import com.example.stockms.model.dto.ErrorDetailDto;
import com.example.stockms.exception.StockValidationException;
import com.example.stockms.utils.DateUtils;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetailDto> handleMethodArgNotValid(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .findFirst()
                .map(err -> err.getDefaultMessage())
                .orElse("Error en la notacion @Valid");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDetailDto(msg, DateUtils.nowString()));
    }

    @ExceptionHandler(StockValidationException.class)
    public ResponseEntity<ErrorDetailDto> handleStockValidation(StockValidationException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDetailDto(ex.getMessage(), DateUtils.nowString()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDetailDto> handleConstraintViolation(ConstraintViolationException ex){
        String msg = ex.getConstraintViolations().stream().findFirst()
                .map(v -> v.getMessage()).orElse("Constraint violation");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDetailDto(msg, DateUtils.nowString()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailDto> handleGeneric(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDetailDto("Internal error", DateUtils.nowString()));
    }
}
