package com.supportsystem.application.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supportsystem.application.domains.Ticket;
import com.supportsystem.application.exceptions.TicketNotFoundException;
import com.supportsystem.application.repositories.TicketRepository;
import com.supportsystem.application.response.dtos.TicketResponseDTO;
import com.supportsystem.application.request.dtos.TicketRequestDTO;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, ModelMapper modelMapper) {
        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public List<TicketResponseDTO> getAllTickets() {
        return ticketRepository.findAll().stream()
            .map(ticket -> modelMapper.map(ticket, TicketResponseDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public TicketResponseDTO getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new TicketNotFoundException(id));
        return modelMapper.map(ticket, TicketResponseDTO.class);
    }

    @Override
    public TicketResponseDTO createTicket(TicketRequestDTO ticketRequestDTO) {
        // Create a new ticket entity
        Ticket ticket = modelMapper.map(ticketRequestDTO, Ticket.class);
        ticketRepository.save(ticket);
        // Return the created ticket as DTO
        return modelMapper.map(ticket, TicketResponseDTO.class);
    }

    @Override
    public TicketResponseDTO updateTicket(Long id, TicketRequestDTO ticketRequestDTO) {
        // Find the ticket to update
        Ticket existingTicket = ticketRepository.findById(id)
            .orElseThrow(() -> new TicketNotFoundException(id));

        // Update the ticket with new values
        existingTicket.setAssigneeId(ticketRequestDTO.getAssigneeId());
        existingTicket.setClientId(ticketRequestDTO.getClientId());
        existingTicket.setDescription(ticketRequestDTO.getDescription());
        existingTicket.setResolution(ticketRequestDTO.getResolution());
        existingTicket.setStatus(ticketRequestDTO.getStatus());
        existingTicket.setLastModified(new Date());
        existingTicket.setModifiedBy(ticketRequestDTO.getModifiedBy());

        ticketRepository.save(existingTicket);

        // Return the updated ticket as DTO
        return modelMapper.map(existingTicket, TicketResponseDTO.class);
    }

    @Override
    public void deleteTicket(Long id) {
        // Ensure the ticket exists before deleting
        Ticket existingTicket = ticketRepository.findById(id)
            .orElseThrow(() -> new TicketNotFoundException(id));

        ticketRepository.delete(existingTicket);
    }
}
