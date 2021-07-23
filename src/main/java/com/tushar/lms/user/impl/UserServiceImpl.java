package com.tushar.lms.user.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tushar.lms.user.entity.UserEntity;
import com.tushar.lms.user.repository.UserRepository;
import com.tushar.lms.user.requestmodel.NewBookRequest;
import com.tushar.lms.user.requestmodel.NewUserRequest;
import com.tushar.lms.user.resilience.BookProxyServiceResilience;
import com.tushar.lms.user.responsemodel.AllUsersListResponse;
import com.tushar.lms.user.responsemodel.GetUserResponse;
import com.tushar.lms.user.responsemodel.IssuedBookResponse;
import com.tushar.lms.user.responsemodel.NewBookResponse;
import com.tushar.lms.user.responsemodel.NewUserResponse;
import com.tushar.lms.user.responsemodel.IssuedBooksForUserResponse;
import com.tushar.lms.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private BookProxyServiceResilience bookProxyServiceResilience;

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public NewUserResponse addNewUser(NewUserRequest addNewUser) {
		logger.info("Inside UserServiceImpl ---------> addNewUser");
		addNewUser.setUserId(UUID.randomUUID().toString());
		addNewUser.setPassword(passwordEncoder.encode(addNewUser.getPassword()));
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity newUser = modelMapper.map(addNewUser, UserEntity.class);
		UserEntity savedUser = userRepository.save(newUser);
		NewUserResponse returnUser = modelMapper.map(savedUser, NewUserResponse.class);
		return returnUser;
	}

	@Override
	public List<AllUsersListResponse> getAllUsers() {
		logger.info("Inside UserServiceImpl ---------> getAllUsers");
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<UserEntity> users = userRepository.findAll();
		List<AllUsersListResponse> userDtos = users.stream()
				.map(user -> modelMapper.map(user, AllUsersListResponse.class)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public GetUserResponse getUser(String userId) {
		logger.info("Inside UserServiceImpl ---------> getUser");
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity found = userRepository.findByUserId(userId);
		GetUserResponse userFound = modelMapper.map(found, GetUserResponse.class);
		return userFound;
	}

	@Override
	public IssuedBooksForUserResponse getIssuedBooksForUser(String userId) {
		logger.info("Inside UserServiceImpl ---------> getIssuedBooksForUser");
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<IssuedBookResponse> bookDtos = bookProxyServiceResilience.getIssuedBooks(userId).getBody();
		GetUserResponse user = getUser(userId);
		IssuedBooksForUserResponse issuedBooksForUser = modelMapper.map(user, IssuedBooksForUserResponse.class);
		issuedBooksForUser.setIssuedBookList(bookDtos);
		return issuedBooksForUser;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Inside UserServiceImpl ---------> loadUserByUsername");
		UserEntity userEntity = userRepository.findByEmail(username);

		if (userEntity == null)
			throw new UsernameNotFoundException("User not available " + username);

		logger.info("Email:" + userEntity.getEmail());

		return new User(userEntity.getEmail(), userEntity.getPassword(), true, true, true, true, new ArrayList<>());
	}

	@Override
	public GetUserResponse getUserDetailsByEmail(String email) {
		logger.info("Inside UserServiceImpl ---------> getUserDetailsByEmail");
		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException("User not available " + email);

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		GetUserResponse getUserResponse = modelMapper.map(userEntity, GetUserResponse.class);

		return getUserResponse;
	}

	@Override
	public NewBookResponse addNewBook(NewBookRequest newBookRequest) {
		logger.info("Inside UserServiceImpl ---------> getIssuedBooksForUser");
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		NewBookResponse response = bookProxyServiceResilience.addNewBook(newBookRequest).getBody();
		return response;
	}
}
