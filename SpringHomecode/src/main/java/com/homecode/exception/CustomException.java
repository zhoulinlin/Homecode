package com.homecode.exception;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String errorMessage;

    public CustomException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
