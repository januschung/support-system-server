package com.supportsystem.application.dtos;

import java.time.LocalDate;
import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
