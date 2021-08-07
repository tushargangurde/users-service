package com.tushar.lms.user.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.tushar.lms.user.requestmodel.NewBookRequest;
import com.tushar.lms.user.requestmodel.NewUserRequest;
import com.tushar.lms.user.responsemodel.AllUsersListResponse;
import com.tushar.lms.user.responsemodel.GetUserResponse;
import com.tushar.lms.user.responsemodel.NewBookResponse;
import com.tushar.lms.user.responsemodel.NewUserResponse;
import com.tushar.lms.user.responsemodel.IssuedBooksForUserResponse;

public interface UserService extends UserDetailsService {

	NewUserResponse addNewUser(NewUserRequest addNewUser);

	List<AllUsersListResponse> getAllUsers();

	GetUserResponse getUser(String userId);

	IssuedBooksForUserResponse getIssuedBooksForUser(String userId, String Authorization);

	GetUserResponse getUserDetailsByEmail(String email);

	NewBookResponse addNewBook(NewBookRequest newBookRequest);

	Boolean issueNewBook(String userId, String bookId, String authorization);

	Boolean returnBook(String userId, String bookId, String authorization);

}
