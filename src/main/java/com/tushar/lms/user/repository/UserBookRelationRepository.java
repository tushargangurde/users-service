package com.tushar.lms.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tushar.lms.user.entity.UserBookRelation;

public interface UserBookRelationRepository extends JpaRepository<UserBookRelation, Integer> {

	UserBookRelation findByUserId(String userId);

}
