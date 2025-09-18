package com.user.exception;

public class DataNotFindException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataNotFindException(String message) {
        super(message);
    }
}
