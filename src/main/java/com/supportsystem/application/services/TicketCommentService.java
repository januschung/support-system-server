package com.supportsystem.application.services;

import com.supportsystem.application.request.dtos.TicketCommentRequestDTO;
import com.supportsystem.application.response.dtos.TicketCommentResponseDTO;


import java.util.List;

public interface TicketCommentService {

	List<TicketCommentResponseDTO> getAllTicketComments();

	TicketCommentResponseDTO getTicketCommentById(Long id);

	List<TicketCommentResponseDTO> getAllTicketCommentsByTicketId(Long id);

	TicketCommentResponseDTO save(TicketCommentRequestDTO ticketCommentRequestDTO);

}
