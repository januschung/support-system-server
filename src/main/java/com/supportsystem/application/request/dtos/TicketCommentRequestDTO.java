package com.supportsystem.application.request.dtos;

import com.supportsystem.application.shared.Status;
import com.supportsystem.application.shared.Status.Resolution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
public class TicketCommentRequestDTO {

	private Long ticketId;

	private String description;
}
