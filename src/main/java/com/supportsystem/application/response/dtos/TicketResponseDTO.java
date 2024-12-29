package com.supportsystem.application.response.dtos;

import java.util.Date;
import java.util.List;

import com.supportsystem.application.shared.Status;

import lombok.Data;

@Data
public class TicketResponseDTO {

	private Long id;

	private Date createdOn;

	private Long createdBy;

	private Long modifiedBy;

	private Date lastModified;

	private Long assigneeId;

	private Long clientId;

	private String description;

	private Status.Ticket status;

	private Status.Resolution resolution;

    private List<TicketCommentResponseDTO> ticketComments;
}
