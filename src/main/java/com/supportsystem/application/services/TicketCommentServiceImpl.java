package com.supportsystem.application.services;

import java.util.List;
import java.util.stream.Collectors;

import com.supportsystem.application.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supportsystem.application.repositories.TicketCommentRepository;
import com.supportsystem.application.response.dtos.TicketCommentResponseDTO;

@Service
public class TicketCommentServiceImpl implements TicketCommentService {

	private TicketCommentRepository ticketCommentRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public TicketCommentServiceImpl(TicketCommentRepository ticketCommentRepository, ModelMapper modelMapper) {
        this.ticketCommentRepository = ticketCommentRepository;
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public List<TicketCommentResponseDTO> getAllTicketCommentsByTicketId(Long ticketId) {
        if (!ticketCommentRepository.existsByTicketId(ticketId)) {
            throw new ResourceNotFoundException("Ticket", "id", ticketId);
        }
        return ticketCommentRepository.findAllByTicketId(ticketId).stream()
            .map(ticketComment -> modelMapper.map(ticketComment, TicketCommentResponseDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public TicketCommentResponseDTO getCommentByTicketIdAndCommentId(Long ticketId, Long commentId) {
        if (!ticketCommentRepository.existsByTicketId(ticketId)) {
            throw new ResourceNotFoundException("Comment", "comment id", commentId);
        }
        return ticketCommentRepository.findByTicketIdAndId(ticketId, commentId)
            .map(ticketComment -> modelMapper.map(ticketComment, TicketCommentResponseDTO.class))
            .orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId));
    }
    
}
