package com.supportsystem.application.services;

import com.supportsystem.application.response.dtos.TicketCommentResponseDTO;


import java.util.List;

public interface TicketCommentService {

	List<TicketCommentResponseDTO> getAllTicketCommentsByTicketId(Long id);

    TicketCommentResponseDTO getCommentByTicketIdAndCommentId(Long ticketId, Long commentId);

}
