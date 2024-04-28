package com.supportsystem.application.services;

import java.util.List;

import com.supportsystem.application.response.dtos.TicketResponseDTO;
import com.supportsystem.application.request.dtos.TicketRequestDTO;

public interface TicketService {

	List<TicketResponseDTO> getAllTickets();

	TicketResponseDTO getTicketById(Long id);

	TicketResponseDTO save(TicketRequestDTO ticketRequestDTO);

}
