package com.craftbeer.api.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
}