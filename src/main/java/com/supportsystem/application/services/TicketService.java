package com.supportsystem.application.services;

import java.util.List;
import java.util.Optional;

import com.supportsystem.application.domains.Ticket;

public interface TicketService {
	
	List<Ticket> getAllTickets();

	Optional<Ticket> getTicketById(Long id);

//	save(Ticket ticket);

}
