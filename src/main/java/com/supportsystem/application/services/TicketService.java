package com.supportsystem.application.services;

import java.util.List;

import com.supportsystem.application.response.dtos.TicketResponseDTO;

public interface TicketService {
	
	List<TicketResponseDTO> getAllTickets();

	TicketResponseDTO getTicketById(Long id);

	TicketResponseDTO save(TicketResponseDTO ticket);


}
