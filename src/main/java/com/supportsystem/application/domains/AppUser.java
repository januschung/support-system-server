package com.supportsystem.application.domains;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "APP_USER")
public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "CREATED_ON", insertable = false)
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Column(name = "MODIFIED_BY")
	private Long modifiedBy;

	@Column(name = "LAST_MODIFIED", insertable = false)
	private Date lastModified;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "PHONE")
	private String phone;
	
	@Column(name = "ENABLED_FL")
	private boolean enabledFl;
	
	@Column(name = "LAST_LOGIN")
	private Date lastLogin;

}
