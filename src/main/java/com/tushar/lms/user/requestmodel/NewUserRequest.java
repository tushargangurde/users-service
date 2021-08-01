package com.tushar.lms.user.requestmodel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewUserRequest {

	private String userId;
	@NotEmpty(message = "Fullname required")
	@Size(min = 6, message = "fullname at least has 6 characters")
	private String fullname;
	@NotEmpty(message = "address required")
	@Size(min = 6, message = "address at least has 6 characters")
	private String address;
	@NotEmpty(message = "email required")
	@Email
	private String email;
	@NotEmpty(message = "password required")
	@Size(min = 6, message = "password at least has 6 characters")
	private String password;
	@NotNull(message = "contact no should not be null")
	private Long contactNo;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getContactNo() {
		return contactNo;
	}

	public void setContactNo(Long contactNo) {
		this.contactNo = contactNo;
	}
}
