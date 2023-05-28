package com.supportsystem.application.domains;

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

@Entity
@Table(name = "TICKET")
public class Ticket {

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Long getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Long assigneeId) {
		this.assigneeId = assigneeId;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status.Ticket getStatus() {
		return status;
	}

	public void setTicketStatus(Status.Ticket status) {
		this.status = status;
	}

	public Status.Resolution getResolution() {
		return resolution;
	}

	public void setResolution(Status.Resolution resolution) {
		this.resolution = resolution;
	}

}
