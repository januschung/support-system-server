package com.supportsystem.application.services;

import java.util.List;

import com.supportsystem.application.response.dtos.TicketDTO;

public interface TicketService {
	
	List<TicketDTO> getAllTickets();

	TicketDTO getTicketById(Long id);

	TicketDTO save(com.supportsystem.application.request.dtos.TicketDTO ticket);


}
