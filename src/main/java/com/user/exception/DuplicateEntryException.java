package com.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) 
public class DuplicateEntryException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DuplicateEntryException(String message) {
        super(message);
    }
}
