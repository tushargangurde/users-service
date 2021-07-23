package com.tushar.lms.user.exception;

import java.time.Instant;

public class ApiErrorResponse {

	private String timeStamp;
	private Integer status;
	private String error;
	private String message;

	public ApiErrorResponse() {
		setTimeStamp(Instant.now().toString());
	}

	public ApiErrorResponse(Integer status, String error, String message) {
		setTimeStamp(Instant.now().toString());
		setStatus(status);
		setError(error);
		setMessage(message);
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
