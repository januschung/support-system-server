package com.supportsystem.application.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supportsystem.application.domains.Ticket;
import com.supportsystem.application.dtos.TicketDTO;
import com.supportsystem.application.exceptions.TicketNotFoundException;
import com.supportsystem.application.repositories.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public List<Ticket> getAllTickets() {
		return ticketRepository.findAll();
	}

	@Override
	public TicketDTO getTicketById(Long id) {
		ticketRepository.findById(id).map(ticket -> {
			return modelMapper.map(ticket, TicketDTO.class);
		}).orElseThrow(() -> new TicketNotFoundException(id));
		return null;
	}

}
