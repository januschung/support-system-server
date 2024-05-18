package com.supportsystem.application.request.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class UserRequestDTO {

	private String username;

	private String email;

	private String firstName;

	private String lastName;

	private String phone;

	private String password;

}
