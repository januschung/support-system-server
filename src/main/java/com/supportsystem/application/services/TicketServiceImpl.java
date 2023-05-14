package com.supportsystem.application.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supportsystem.application.domains.Ticket;
import com.supportsystem.application.exceptions.TicketNotFoundException;
import com.supportsystem.application.repositories.TicketRepository;
import com.supportsystem.application.response.dtos.TicketDTO;
import com.supportsystem.application.shared.Status;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public List<TicketDTO> getAllTickets() {
		return ticketRepository.findAll().stream().map(ticket -> {
			return modelMapper.map(ticket, TicketDTO.class);
		}).collect(Collectors.toList());
	}

	@Override
	public TicketDTO getTicketById(Long id) {
		return ticketRepository.findById(id).map(ticket -> {
			return modelMapper.map(ticket, TicketDTO.class);
		}).orElseThrow(() -> new TicketNotFoundException(id));
	}

	@Override
	public TicketDTO save(com.supportsystem.application.request.dtos.TicketDTO ticketDTO) {
//	    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		Ticket entity = modelMapper.map(ticketDTO, Ticket.class);
		entity.setCreatedBy(-1L);
		entity.setModifiedBy(-1L);
		entity.setTicketStatus(Status.Ticket.NEW);
		entity.setAssigneeId(-1L);
		ticketRepository.save(entity);
		return modelMapper.map(entity, TicketDTO.class);
	}

}
