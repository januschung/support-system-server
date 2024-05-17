package com.supportsystem.application.domains;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "tickets")
@Table(name = "app_user")
public class AppUser implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "created_on", insertable = false)
	private Date createdOn;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "modified_by")
	private Long modifiedBy;

	@Column(name = "last_modified", insertable = false)
	private Date lastModified;

	private String username;

	private String password;

	private String email;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	private String phone;
	
	@Column(name = "enable_fl")
	private boolean enableFl;
	
	@Column(name = "last_login")
	private Date lastLogin;

	@OneToMany(targetEntity=Ticket.class,cascade = CascadeType.ALL,
		fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "assigneeId")
    private List<Ticket> tickets;

}
