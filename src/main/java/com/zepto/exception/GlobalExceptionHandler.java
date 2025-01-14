package com.zepto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ItemNotFoundException.class)
	public ResponseEntity<String> handleItemNotFound(ItemNotFoundException ex ){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
		
//	 @ExceptionHandler(Exception.class)
//	    public ResponseEntity<String> handleException(Exception e) {
//	        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
	}

}