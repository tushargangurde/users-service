package com.tushar.lms.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tushar.lms.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	UserEntity findByUserId(String userId);
	
	UserEntity findByEmail(String email);

}
