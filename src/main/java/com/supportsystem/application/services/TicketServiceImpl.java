package com.supportsystem.application.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supportsystem.application.domains.Ticket;
import com.supportsystem.application.exceptions.TicketNotFoundException;
import com.supportsystem.application.repositories.TicketRepository;
import com.supportsystem.application.response.dtos.TicketResponseDTO;
import com.supportsystem.application.request.dtos.TicketRequestDTO;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public List<TicketResponseDTO> getAllTickets() {
		return ticketRepository.findAll().stream().map(ticket -> {
			return modelMapper.map(ticket, TicketResponseDTO.class);
		}).collect(Collectors.toList());
	}

	@Override
	public TicketResponseDTO getTicketById(Long id) {
		return ticketRepository.findById(id).map(ticket -> {
			return modelMapper.map(ticket, TicketResponseDTO.class);
		}).orElseThrow(() -> new TicketNotFoundException(id));
	}

	@Override
	public TicketResponseDTO save(TicketRequestDTO ticketRequestDTO) {
		Ticket entity = modelMapper.map(ticketRequestDTO, Ticket.class);
		entity.setCreatedBy(-1L);
		ticketRepository.save(entity);
		return modelMapper.map(entity, TicketResponseDTO.class);
	}

}
