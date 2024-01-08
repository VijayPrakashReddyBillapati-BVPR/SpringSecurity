package com.authserver.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.authserver.controllers.UserResource;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(UserResource.class);
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetails> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
		
        BindingResult bindingResult = ex.getBindingResult();
        String errorMessage = bindingResult.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining("; ", "Validation error. Check your inputs. ", ""));
        logger.error("Validation error: {}", errorMessage);
        
        return ResponseEntity.badRequest().body(new ErrorDetails(LocalDateTime.now(),errorMessage, request.getDescription(false)));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetails> handleConstraintViolationException(ConstraintViolationException e, WebRequest request) {
        String errorMessage = "Constraint Validation error. Check your inputs."+e.getMessage();
        logger.error("Constraint Validation error: {}", e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorDetails(LocalDateTime.now(),errorMessage, request.getDescription(false)));
    }
    
}
