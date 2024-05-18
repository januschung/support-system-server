package com.supportsystem.application.response.dtos;

import java.util.Date;
import java.util.List;

import com.supportsystem.application.domains.Ticket;
import lombok.Data;

@Data
public class UserResponseDTO {

	private Long id;

	private String username;

	private String email;

	private String firstName;

	private String lastName;

	private String phone;

	private boolean enableFl;

	private Date lastLogin;

	private List<Ticket> tickets;

}
