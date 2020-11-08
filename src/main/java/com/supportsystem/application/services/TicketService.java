package com.supportsystem.application.services;

import java.util.List;

import com.supportsystem.application.domains.Ticket;
import com.supportsystem.application.dtos.TicketDTO;

public interface TicketService {
	
	List<Ticket> getAllTickets();

	TicketDTO getTicketById(Long id);

//	save(Ticket ticket);

}
