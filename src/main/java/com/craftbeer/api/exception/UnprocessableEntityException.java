package com.craftbeer.api.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UnprocessableEntityException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private ErrorResponse error;
	
	public UnprocessableEntityException(String message) {
		this.error = new ErrorResponse(message);
	}
}