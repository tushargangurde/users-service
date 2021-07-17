package com.tushar.lms.user.proxyservices;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tushar.lms.user.dto.IssuedBookDto;

@FeignClient(name = "books-service", path = "/book")
public interface BookProxyService {

	@GetMapping("/issuedBooks/{userId}")
	public ResponseEntity<List<IssuedBookDto>> getIssuedBooks(@PathVariable String userId);

}
