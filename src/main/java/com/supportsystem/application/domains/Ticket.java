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

import com.supportsystem.application.shared.Status;
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
@ToString
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

}
