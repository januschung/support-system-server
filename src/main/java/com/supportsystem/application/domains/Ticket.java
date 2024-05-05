package com.supportsystem.application.domains;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import com.supportsystem.application.shared.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "TICKET")
public class Ticket implements Serializable {

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

	@Column(name = "ASSIGNEE_ID")
	private Long assigneeId;

	@Column(name = "CLIENT_ID")
	private Long clientId;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private Status.Ticket status;

	@Column(name = "RESOLUTION")
	@Enumerated(EnumType.STRING)
	private Status.Resolution resolution;

	@ManyToOne
	@JoinColumn(name = "assigneeId")
	private AppUser appUser;

	public Ticket(AppUser appUser) {
		this.appUser = appUser;
	}

	@Override
	public String toString() {
		return "Ticket{" +
			"id=" + id +
			", createdOn=" + createdOn +
			", createdBy=" + createdBy +
			", modifiedBy=" + modifiedBy +
			", lastModified=" + lastModified +
			", assigneeId=" + assigneeId +
			", clientId=" + clientId +
			", description='" + description + '\'' +
			", status=" + status +
			", resolution=" + resolution +
			", appUser=" + appUser.getId() +
			'}';
	}
}
