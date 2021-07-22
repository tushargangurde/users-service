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

import com.tushar.lms.user.dto.IssuedBookDto;
import com.tushar.lms.user.dto.ResponseIssuedBooksForUser;
import com.tushar.lms.user.dto.UserDto;
import com.tushar.lms.user.entity.UserEntity;
import com.tushar.lms.user.repository.UserRepository;
import com.tushar.lms.user.resilience.BookProxyServiceResilience;
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
	public UserDto addNewUser(UserDto addNewUser) {
		logger.info("Inside UserServiceImpl ---------> addNewUser");
		addNewUser.setUserId(UUID.randomUUID().toString());
		addNewUser.setPassword(passwordEncoder.encode(addNewUser.getPassword()));
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity newUser = modelMapper.map(addNewUser, UserEntity.class);
		UserEntity savedUser = userRepository.save(newUser);
		UserDto returnUser = modelMapper.map(savedUser, UserDto.class);
		return returnUser;
	}

	@Override
	public List<UserDto> getAllUsers() {
		logger.info("Inside UserServiceImpl ---------> getAllUsers");
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<UserEntity> users = userRepository.findAll();
		logger.info(users.toString());
		List<UserDto> userDtos = users.stream().map(user -> modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());
		logger.info(userDtos.toString());
		return userDtos;
	}

	@Override
	public UserDto getUser(String userId) {
		logger.info("Inside UserServiceImpl ---------> getUser");
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity found = userRepository.findByUserId(userId);
		UserDto userFound = modelMapper.map(found, UserDto.class);
		return userFound;
	}

	@Override
	public ResponseIssuedBooksForUser getIssuedBooksForUser(String userId) {
		logger.info("Inside UserServiceImpl ---------> getIssuedBooksForUser");
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<IssuedBookDto> bookDtos = bookProxyServiceResilience.getIssuedBooks(userId).getBody();
		UserDto user = getUser(userId);
		ResponseIssuedBooksForUser issuedBooksForUser = modelMapper.map(user, ResponseIssuedBooksForUser.class);
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
	public UserDto getUserDetailsByEmail(String email) {
		logger.info("Inside UserServiceImpl ---------> getUserDetailsByEmail");
		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException("User not available " + email);
		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto = modelMapper.map(userEntity, UserDto.class);

		return userDto;
	}
}
