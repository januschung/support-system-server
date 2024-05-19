package com.supportsystem.application.response.dtos;

import com.supportsystem.application.shared.Status;
import com.supportsystem.application.shared.Status.Resolution;
import lombok.Data;

import java.util.Date;

@Data
public class TicketCommentResponseDTO {

	private Long ticketId;

	private Date createdOn;

	private Long createdBy;

	private String description;
}
