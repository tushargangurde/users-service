package com.tushar.lms.user.exception;

public class CustomJwtException extends RuntimeException {

	private static final long serialVersionUID = -4699605265336060066L;

	public CustomJwtException(String message) {
		super(message);
	}

	public CustomJwtException() {

	}

}
