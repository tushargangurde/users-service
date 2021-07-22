package com.tushar.lms.user.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.tushar.lms.user.requestmodel.NewUserRequest;
import com.tushar.lms.user.responsemodel.AllUsersListResponse;
import com.tushar.lms.user.responsemodel.GetUserResponse;
import com.tushar.lms.user.responsemodel.NewUserResponse;
import com.tushar.lms.user.responsemodel.ResponseIssuedBooksForUser;

public interface UserService extends UserDetailsService{

	NewUserResponse addNewUser(NewUserRequest addNewUser);

	List<AllUsersListResponse> getAllUsers();

	GetUserResponse getUser(String userId);

	ResponseIssuedBooksForUser getIssuedBooksForUser(String userId);
	
	GetUserResponse getUserDetailsByEmail(String email);

}
