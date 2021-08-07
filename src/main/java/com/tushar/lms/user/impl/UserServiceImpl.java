package com.tushar.lms.user.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tushar.lms.user.entity.UserBookRelation;
import com.tushar.lms.user.entity.UserEntity;
import com.tushar.lms.user.repository.UserBookRelationRepository;
import com.tushar.lms.user.repository.UserRepository;
import com.tushar.lms.user.requestmodel.NewBookRequest;
import com.tushar.lms.user.requestmodel.NewUserRequest;
import com.tushar.lms.user.resilience.BookProxyServiceResilience;
import com.tushar.lms.user.responsemodel.AllUsersListResponse;
import com.tushar.lms.user.responsemodel.GetBookResponse;
import com.tushar.lms.user.responsemodel.GetUserResponse;
import com.tushar.lms.user.responsemodel.IssuedBookResponse;
import com.tushar.lms.user.responsemodel.IssuedBooksForUserResponse;
import com.tushar.lms.user.responsemodel.NewBookResponse;
import com.tushar.lms.user.responsemodel.NewUserResponse;
import com.tushar.lms.user.service.UserService;
import com.tushar.lms.user.utility.SMS;
import com.tushar.lms.user.utility.SmsPublisher;

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

	@Autowired
	private UserBookRelationRepository userBookRelationRepository;

	@Autowired
	private SmsPublisher smsPublisher;

	@Autowired
	private SMS sms;

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public NewUserResponse addNewUser(NewUserRequest addNewUser) {
		logger.info("Inside UserServiceImpl ---------> addNewUser");
		addNewUser.setUserId(UUID.randomUUID().toString());
		addNewUser.setPassword(passwordEncoder.encode(addNewUser.getPassword()));
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity newUser = modelMapper.map(addNewUser, UserEntity.class);
		newUser.setRole("ROLE_USER");
		UserEntity savedUser = userRepository.save(newUser);
		String msg = "User is registered successfully with User ID:" + savedUser.getUserId()
				+ ". Please use your email as username to login.";
		sms.setMessage(msg);
		sms.setContactNo(savedUser.getContactNo());
		smsPublisher.sendMessage(sms);
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
	public IssuedBooksForUserResponse getIssuedBooksForUser(String userId, String authorization) {
		logger.info("Inside UserServiceImpl ---------> getIssuedBooksForUser");
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<IssuedBookResponse> bookDtos = bookProxyServiceResilience.getIssuedBooks(userId, authorization).getBody();
		GetUserResponse user = getUser(userId);
		IssuedBooksForUserResponse issuedBooksForUser = modelMapper.map(user, IssuedBooksForUserResponse.class);
		issuedBooksForUser.setIssuedBookList(bookDtos);
		return issuedBooksForUser;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Inside UserServiceImpl ---------> loadUserByUsername");
		Optional<UserEntity> userEntity = userRepository.findByEmail(username);

		if (userEntity.isEmpty())
			throw new UsernameNotFoundException("User not available " + username);

		return new CustomUserDetails(userEntity.get());

	}

	@Override
	public GetUserResponse getUserDetailsByEmail(String email) {
		logger.info("Inside UserServiceImpl ---------> getUserDetailsByEmail");
		Optional<UserEntity> userEntity = userRepository.findByEmail(email);

		if (userEntity.isEmpty())
			throw new UsernameNotFoundException("User not available " + email);

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		GetUserResponse getUserResponse = modelMapper.map(userEntity.get(), GetUserResponse.class);

		return getUserResponse;
	}

	@Override
	public NewBookResponse addNewBook(NewBookRequest newBookRequest) {
		logger.info("Inside UserServiceImpl ---------> getIssuedBooksForUser");
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		NewBookResponse response = bookProxyServiceResilience.addNewBook(newBookRequest).getBody();
		return response;
	}

	@Override
	@Transactional
	public Boolean issueNewBook(String userId, String bookId, String authorization) {
		Boolean result = false;
		logger.info("Inside UserServiceImpl ---------> issueNewBook");
		GetBookResponse response = bookProxyServiceResilience.getBook(bookId, authorization).getBody();
		logger.info(response.toString());
		if (!response.getAvailable() || response == null) {
			return result;
		} else {
			UserBookRelation userBookRelation = userBookRelationRepository.findByUserId(userId);
			if (userBookRelation == null) {
				UserBookRelation newUser = new UserBookRelation();
				newUser.setUserId(userId);
				newUser.setBookCount(1);
				UserBookRelation issueBookToUser = userBookRelationRepository.save(newUser);
				if (issueBookToUser != null) {
					Boolean flag = bookProxyServiceResilience.setAvailableStatus(bookId, userId, authorization)
							.getBody();
					if (flag) {
						logger.info("Book status set to unavailable");
						logger.info("Book with " + bookId + " issued successfully to " + userId);
						String msg = "Book with " + bookId + " issued successfully to " + userId;
						sms.setMessage(msg);
						sms.setContactNo(getUser(userId).getContactNo());
						smsPublisher.sendMessage(sms);
					} else {
						logger.info("Something went wrong while updating book status");
					}
				}
				result = true;
			} else if (userBookRelation.getBookCount() < 2) {
				int count = userBookRelation.getBookCount();
				userBookRelation.setBookCount(count + 1);
				UserBookRelation issueBookToUser = userBookRelationRepository.save(userBookRelation);
				if (issueBookToUser != null) {
					Boolean flag = bookProxyServiceResilience.setAvailableStatus(bookId, userId, authorization)
							.getBody();
					if (flag) {
						logger.info("Book status set to unavailable");
						logger.info("Book with " + bookId + " issued successfully to " + userId);
						String msg = "Book with " + bookId + " issued successfully to " + userId;
						sms.setMessage(msg);
						sms.setContactNo(getUser(userId).getContactNo());
						smsPublisher.sendMessage(sms);
					} else {
						logger.info("Something went wrong while updating book status");
					}
				}
				result = true;
			} else {
				logger.info("Already reached to maximum book limit. Can not issue more book");
				return result;
			}
		}

		return result;
	}
}
