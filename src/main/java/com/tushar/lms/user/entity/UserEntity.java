package com.tushar.lms.user.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserEntity {

	@Id
	@GeneratedValue
	private int id;
	private String userId;
	private String fullname;
	private String address;
	private String email;
	private String password;
	private Long contactNo;

	public UserEntity() {
	}

	public UserEntity(int id, String userId, String fullname, String address, Long contactNo, String email,
			String password) {
		this.id = id;
		this.userId = userId;
		this.fullname = fullname;
		this.address = address;
		this.contactNo = contactNo;
		this.email = email;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", userId=" + userId + ", fullname=" + fullname + ", address=" + address
				+ ", contactNo=" + contactNo + ", email=" + email + ", password=" + password + "]";
	}

}
