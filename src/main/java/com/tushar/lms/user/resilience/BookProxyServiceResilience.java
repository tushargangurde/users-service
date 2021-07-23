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
import com.tushar.lms.user.requestmodel.NewBookRequest;
import com.tushar.lms.user.responsemodel.IssuedBookResponse;
import com.tushar.lms.user.responsemodel.NewBookResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class BookProxyServiceResilience {

	@Autowired
	private BookProxyService bookProxyService;

	Logger logger = LoggerFactory.getLogger(BookProxyServiceResilience.class);

	long count = 1;

	@CircuitBreaker(name = "books-service", fallbackMethod = "fallbackGetIssuedBooks")
	public ResponseEntity<List<IssuedBookResponse>> getIssuedBooks(String userId) {
		logger.info("Inside BookProxyServiceResilience ---------> getIssuedBooks");
		logger.info("count=" + count);
		count++;
		logger.info("Calling bookProxyService.getIssuedBooks");
		return bookProxyService.getIssuedBooks(userId);
	}

	public ResponseEntity<List<IssuedBookResponse>> fallbackGetIssuedBooks(String userId, Throwable th) {
		logger.error("Error:" + th.getMessage());
		List<IssuedBookResponse> bookDtos = new ArrayList<>();
		return new ResponseEntity<List<IssuedBookResponse>>(bookDtos, HttpStatus.BAD_GATEWAY);
	}

	public ResponseEntity<NewBookResponse> addNewBook(NewBookRequest newBookRequest) {
		logger.info("Inside BookProxyServiceResilience ---------> addNewBook");
		return bookProxyService.addNewBook(newBookRequest);
	}

}
