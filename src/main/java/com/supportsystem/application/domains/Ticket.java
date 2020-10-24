package com.supportsystem.application.domains;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TICKET")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Column(name = "MODIFIED_BY")
	private Long modifiedBy;

	@Column(name = "LAST_MODIFIED")
	private Date lastModified;

	@Column(name = "ASSIGNEE_ID")
	private Long assigneeId;

	@Column(name = "CLIENT_ID")
	private Long clientId;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "TICKET_STATUS_ID")
	private Long ticketStatusId;

	@Column(name = "RESOLUTION_ID")
	private Long resolutionId;

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

	public Long getTicketStatusId() {
		return ticketStatusId;
	}

	public void setTicketStatusId(Long ticketStatusId) {
		this.ticketStatusId = ticketStatusId;
	}

	public Long getResolutionId() {
		return resolutionId;
	}

	public void setResolutionId(Long resolutionId) {
		this.resolutionId = resolutionId;
	}

}
