package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;


import org.springframework.web.bind.MethodArgumentNotValidException;


import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;
import java.util.HashMap;


@RestControllerAdvice
public class ErrorHandler {
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    
  public Map<String, String> ErrorHash(MethodArgumentNotValidException e){
  
      
      Map<String, String> errors = new HashMap<>();
  
 for(FieldError error : e.getBindingResult().getFieldErrors()){
 
 
     String fieldName = error.getField();
     String errorMessage = error.getDefaultMessage();
 
     
     errors.put(fieldName, errorMessage);
 } 
  
  
 return errors;
  }  
    
    
}
