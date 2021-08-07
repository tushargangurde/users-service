package com.tushar.lms.user.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_book")
public class UserBookRelation {

	@Id
	@GeneratedValue
	private int id;
	private String userId;
	private int bookCount;

	public UserBookRelation() {
	}

	public UserBookRelation(int id, String userId, int bookCount) {
		this.id = id;
		this.userId = userId;
		this.bookCount = bookCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getBookCount() {
		return bookCount;
	}

	public void setBookCount(int bookCount) {
		this.bookCount = bookCount;
	}

}
