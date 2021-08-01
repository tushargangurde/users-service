package com.tushar.lms.user.utility;

import org.springframework.stereotype.Component;

@Component
public class SMS {

	private Long contactNo;
	private String message;

	public Long getContactNo() {
		return contactNo;
	}

	public void setContactNo(Long contactNo) {
		this.contactNo = contactNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "SMS [contactNo=" + contactNo + ", message=" + message + "]";
	}

}
