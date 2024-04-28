package com.supportsystem.application.response.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class UserDTO {

	private Long id;

	private String username;

	private String email;

	private String firstName;

	private String lastName;

	private String phone;

	private boolean enabledFl;

	private Date lastLogin;

}
