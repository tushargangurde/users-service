package com.tushar.lms.user.dto;

import java.util.List;

public class ResponseIssuedBooksForUser {

	private String userId;
	private String fullname;
	private String address;
	private long contactNo;
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

	public long getContactNo() {
		return contactNo;
	}

	public void setContactNo(long contactNo) {
		this.contactNo = contactNo;
	}

	public List<IssuedBookDto> getIssuedBookList() {
		return issuedBookList;
	}

	public void setIssuedBookList(List<IssuedBookDto> issuedBookList) {
		this.issuedBookList = issuedBookList;
	}
}
