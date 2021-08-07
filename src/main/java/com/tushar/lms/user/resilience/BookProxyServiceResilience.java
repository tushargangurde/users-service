package com.tushar.lms.user.resilience;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tushar.lms.user.proxyservices.BookProxyService;
import com.tushar.lms.user.responsemodel.GetBookResponse;
import com.tushar.lms.user.responsemodel.IssuedBookResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class BookProxyServiceResilience {

	@Autowired
	private BookProxyService bookProxyService;

	Logger logger = LoggerFactory.getLogger(BookProxyServiceResilience.class);

	@CircuitBreaker(name = "books-service", fallbackMethod = "fallbackGetIssuedBooks")
	public ResponseEntity<List<IssuedBookResponse>> getIssuedBooks(String userId, String authorization) {
		logger.info("Inside BookProxyServiceResilience ---------> getIssuedBooks");
		logger.info("Calling bookProxyService.getIssuedBooks");
		return bookProxyService.getIssuedBooks(userId, authorization);
	}

	public ResponseEntity<List<IssuedBookResponse>> fallbackGetIssuedBooks(String userId, String Authorization,
			Throwable th) {
		logger.info("Inside BookProxyServiceResilience ---------> fallbackGetIssuedBooks");
		logger.error("Error:" + th.getMessage());
		List<IssuedBookResponse> bookDtos = new ArrayList<>();
		return new ResponseEntity<List<IssuedBookResponse>>(bookDtos, HttpStatus.BAD_GATEWAY);
	}

	@CircuitBreaker(name = "books-service", fallbackMethod = "fallbackGetBook")
	public ResponseEntity<GetBookResponse> getBook(String bookId, String authorization) {
		logger.info("Inside BookProxyServiceResilience ---------> getBook");
		logger.info("Calling bookProxyService.getBook");
		return bookProxyService.getBook(bookId, authorization);

	}

	public ResponseEntity<GetBookResponse> fallbackGetBook(String bookId, String Authorization, Throwable th) {
		logger.info("Inside BookProxyServiceResilience ---------> fallbackGetBook");
		logger.error("Error:" + th.getMessage());
		return new ResponseEntity<GetBookResponse>(new GetBookResponse(), HttpStatus.NOT_FOUND);
	}

	@CircuitBreaker(name = "books-service", fallbackMethod = "fallbackSetAvailableStatus")
	public ResponseEntity<Boolean> setAvailableStatus(String bookId, String userId, String authorization) {
		logger.info("Inside BookProxyServiceResilience ---------> setAvailableStatus");
		logger.info("Calling bookProxyService.setAvailableStatus");
		return bookProxyService.setAvailableStatus(bookId, userId, authorization);
	}

	public ResponseEntity<Boolean> fallbackSetAvailableStatus(String bookId, String userId, String Authorization,
			Throwable th) {
		logger.info("Inside BookProxyServiceResilience ---------> fallbackSetAvailableStatus");
		logger.error("Error:" + th.getMessage());
		return new ResponseEntity<Boolean>(false, HttpStatus.NOT_MODIFIED);
	}

}
