package com.tushar.lms.user.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tushar.lms.user.requestmodel.NewUserRequest;
import com.tushar.lms.user.responsemodel.AllUsersListResponse;
import com.tushar.lms.user.responsemodel.GetUserResponse;
import com.tushar.lms.user.responsemodel.NewUserResponse;
import com.tushar.lms.user.responsemodel.ResponseIssuedBooksForUser;
import com.tushar.lms.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Value("${service.test}")
	private String test;

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@PostMapping("/add")
	public ResponseEntity<NewUserResponse> addUser(@RequestBody NewUserRequest addNewUser) {
		logger.info("Inside UserController ---------> addUser");
		NewUserResponse newUser = userService.addNewUser(addNewUser);
		return new ResponseEntity<NewUserResponse>(newUser, HttpStatus.CREATED);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<AllUsersListResponse>> getAllUsers() {
		logger.info("Inside UserController ---------> getAllUsers");
		List<AllUsersListResponse> allUsers = userService.getAllUsers();
		return new ResponseEntity<List<AllUsersListResponse>>(allUsers, HttpStatus.FOUND);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<GetUserResponse> getUser(@PathVariable String userId) {
		logger.info("Inside UserController ---------> getUser");
		GetUserResponse user = userService.getUser(userId);
		return new ResponseEntity<GetUserResponse>(user, HttpStatus.FOUND);
	}

	@GetMapping("/getBooksForUser/{userId}")
	public ResponseEntity<ResponseIssuedBooksForUser> getIssuedBooksForUser(@PathVariable String userId) {
		logger.info("Inside UserController ---------> getIssuedBooksForUser");
		ResponseIssuedBooksForUser issuedBooksForUser = userService.getIssuedBooksForUser(userId);
		return new ResponseEntity<ResponseIssuedBooksForUser>(issuedBooksForUser, HttpStatus.ACCEPTED);
	}

	@GetMapping("/test")
	public ResponseEntity<String> getProfiles() {
		logger.info("Inside UserController ---------> getProfiles");
		return new ResponseEntity<String>(test, HttpStatus.OK);
	}
}
