package com.tushar.lms.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tushar.lms.user.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUserId(String userId);

}
