package com.supportsystem.application.response.dtos;

import java.util.Date;

public class UserDTO {

	private Long id;
	
	private String username;
	
	private String email;

	private String firstName;
	
	private String lastName;
	
	private String phone;
	
	private boolean enabledFl;
	
	private Date lastLogin;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isEnabledFl() {
		return enabledFl;
	}

	public void setEnabledFl(boolean enabledFl) {
		this.enabledFl = enabledFl;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

}
