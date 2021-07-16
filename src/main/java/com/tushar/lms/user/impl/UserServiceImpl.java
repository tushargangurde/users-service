package com.tushar.lms.user.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tushar.lms.user.dto.IssuedBookDto;
import com.tushar.lms.user.dto.ResponseIssuedBooksForUser;
import com.tushar.lms.user.dto.UserDto;
import com.tushar.lms.user.entity.User;
import com.tushar.lms.user.proxyservices.BookProxyService;
import com.tushar.lms.user.repository.UserRepository;
import com.tushar.lms.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookProxyService bookProxyService;

	@Override
	public UserDto addNewUser(UserDto addNewUser) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		User newUser = modelMapper.map(addNewUser, User.class);
		newUser.setUserId(UUID.randomUUID().toString());
		User savedUser = userRepository.save(newUser);
		UserDto returnUser = modelMapper.map(savedUser, UserDto.class);
		return returnUser;
	}

	@Override
	public List<UserDto> getAllUsers() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<User> users = userRepository.findAll();
		List<UserDto> userDtos = users.stream().map(user -> modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public UserDto getUser(String userId) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		User found = userRepository.findByUserId(userId);
		UserDto userFound = modelMapper.map(found, UserDto.class);
		return userFound;
	}

	@Override
	public ResponseIssuedBooksForUser getIssuedBooksForUser(String userId) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<IssuedBookDto> bookDtos = bookProxyService.getIssuedBooks(userId).getBody();
		UserDto user = getUser(userId);
		ResponseIssuedBooksForUser issuedBooksForUser = modelMapper.map(user, ResponseIssuedBooksForUser.class);
		issuedBooksForUser.setIssuedBookList(bookDtos);
		return issuedBooksForUser;
	}

}
