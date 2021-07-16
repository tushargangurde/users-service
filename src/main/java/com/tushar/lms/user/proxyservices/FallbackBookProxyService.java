package com.tushar.lms.user.proxyservices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tushar.lms.user.dto.IssuedBookDto;

@Component
public class FallbackBookProxyService implements BookProxyService {

	@Override
	public ResponseEntity<List<IssuedBookDto>> getIssuedBooks(String userId) {
		List<IssuedBookDto> bookDtos = new ArrayList<>();
		return new ResponseEntity<List<IssuedBookDto>>(bookDtos, HttpStatus.BAD_GATEWAY);
	}

}
