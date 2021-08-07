package com.tushar.lms.user.exception;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		logger.info("GlobalExceptionHandler --------------------> handleMethodArgumentNotValid");

		String ERROR_MESSAGE = exception.getMessage();
		try {
			ERROR_MESSAGE = exception.getBindingResult().getFieldErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
		} catch (Exception e) {
			logger.error("Error constructing error message", e);
		}

		ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(),
				"Please fill mandatory parameters", ERROR_MESSAGE);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleException(Exception exception) {
		logger.info("GlobalExceptionHandler --------------------> handleException");
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Something went wrong", exception.getMessage());
		return new ResponseEntity<ApiErrorResponse>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CustomJwtException.class)
	public ResponseEntity<ApiErrorResponse> handleCustomJwtException(CustomJwtException exception) {
		logger.info("GlobalExceptionHandler --------------------> handleCustomJwtException");
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.UNAUTHORIZED.value(),
				"JWT related exception", exception.getMessage());
		return new ResponseEntity<ApiErrorResponse>(apiErrorResponse, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(AccessDeniedException exception) {
		logger.info("GlobalExceptionHandler --------------------> handleAccessDeniedException");
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Unauthorized",
				exception.getMessage());
		return new ResponseEntity<ApiErrorResponse>(apiErrorResponse, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleBookNotFoundException(Exception exception) {
		logger.info("GlobalExceptionHandler --------------------> handleBookNotFoundException");
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), "Book Not found",
				exception.getMessage());
		return new ResponseEntity<ApiErrorResponse>(apiErrorResponse, HttpStatus.NOT_FOUND);
	}
}
