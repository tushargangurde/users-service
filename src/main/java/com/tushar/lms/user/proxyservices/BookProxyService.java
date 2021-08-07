package com.tushar.lms.user.proxyservices;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.tushar.lms.user.responsemodel.GetBookResponse;
import com.tushar.lms.user.responsemodel.IssuedBookResponse;

@FeignClient(name = "books-service", path = "/book")
public interface BookProxyService {

	@GetMapping("/issuedBooks/{userId}")
	public ResponseEntity<List<IssuedBookResponse>> getIssuedBooks(@PathVariable String userId,
			@RequestHeader String Authorization);

	@GetMapping("/{bookId}")
	public ResponseEntity<GetBookResponse> getBook(@PathVariable String bookId, @RequestHeader String Authorization);

	@PostMapping("/setAvailableStatus/{bookId}/{userId}")
	public ResponseEntity<Boolean> setAvailableStatus(@PathVariable String bookId, @PathVariable String userId,
			@RequestHeader String Authorization);

}
