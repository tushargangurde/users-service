package com.tushar.lms.user.responsemodel;

import java.util.List;

import com.tushar.lms.user.dto.IssuedBookDto;

public class ResponseIssuedBooksForUser {

	private String userId;
	private String fullname;
	private String address;
	private String email;
	private Long contactNo;
	private List<IssuedBookDto> issuedBookList;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getContactNo() {
		return contactNo;
	}

	public void setContactNo(Long contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<IssuedBookDto> getIssuedBookList() {
		return issuedBookList;
	}

	public void setIssuedBookList(List<IssuedBookDto> issuedBookList) {
		this.issuedBookList = issuedBookList;
	}
}
