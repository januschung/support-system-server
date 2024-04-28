package com.supportsystem.application.request.dtos;

import java.util.Date;

import com.supportsystem.application.shared.Status;
import com.supportsystem.application.shared.Status.Resolution;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketRequestDTO {

	private Long createdBy;

	private Long modifiedBy;

	private Date lastModified;

	private String description;

	private Long assigneeId;

	private Long clientId;

	private Status.Ticket status;

	private Status.Resolution resolution;
}
