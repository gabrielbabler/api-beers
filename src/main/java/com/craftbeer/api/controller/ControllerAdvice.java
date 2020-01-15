package com.craftbeer.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.craftbeer.api.exception.ErrorResponse;
import com.craftbeer.api.exception.NotFoundException;
import com.craftbeer.api.exception.UnprocessableEntityException;

@RestControllerAdvice
public class ControllerAdvice {
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleServiceUnavailable(IllegalArgumentException e) {
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body("Aborting request. Reason: " + e.getMessage());
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.build();
	}
	
	@ExceptionHandler(UnprocessableEntityException.class)
	public ResponseEntity<ErrorResponse> handleUnprocessableEntity(UnprocessableEntityException e) {
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.body(e.getError());
	}
}
