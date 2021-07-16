package com.tushar.lms.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tushar.lms.user.dto.ResponseIssuedBooksForUser;
import com.tushar.lms.user.dto.UserDto;
import com.tushar.lms.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/add")
	public ResponseEntity<UserDto> addUser(@RequestBody UserDto addNewUser) {
		UserDto newUser = userService.addNewUser(addNewUser);
		return new ResponseEntity<UserDto>(newUser, HttpStatus.CREATED);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		List<UserDto> allUsers = userService.getAllUsers();
		return new ResponseEntity<List<UserDto>>(allUsers, HttpStatus.FOUND);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable String userId) {
		UserDto user = userService.getUser(userId);
		return new ResponseEntity<UserDto>(user, HttpStatus.FOUND);
	}

	@GetMapping("/getBooksForUser/{userId}")
	public ResponseEntity<ResponseIssuedBooksForUser> getIssuedBooksForUser(@PathVariable String userId) {
		ResponseIssuedBooksForUser issuedBooksForUser = userService.getIssuedBooksForUser(userId);
		return new ResponseEntity<ResponseIssuedBooksForUser>(issuedBooksForUser, HttpStatus.OK);
	}
}
