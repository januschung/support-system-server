package com.supportsystem.application.services;

import java.util.List;

import com.supportsystem.application.dtos.TicketDTO;

public interface TicketService {
	
	List<TicketDTO> getAllTickets();

	TicketDTO getTicketById(Long id);

//	save(Ticket ticket);

}
