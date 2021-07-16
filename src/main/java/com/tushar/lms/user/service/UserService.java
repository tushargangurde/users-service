package com.tushar.lms.user.service;

import java.util.List;

import com.tushar.lms.user.dto.ResponseIssuedBooksForUser;
import com.tushar.lms.user.dto.UserDto;

public interface UserService {

	UserDto addNewUser(UserDto addNewUser);

	List<UserDto> getAllUsers();

	UserDto getUser(String userId);

	ResponseIssuedBooksForUser getIssuedBooksForUser(String userId);

}
