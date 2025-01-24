package com.supportsystem.application.services;

import com.supportsystem.application.exceptions.ResourceNotFoundException;
import com.supportsystem.application.repositories.TicketCommentRepository;
import com.supportsystem.application.response.dtos.TicketCommentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketCommentServiceImpl implements TicketCommentService {

	private final TicketCommentRepository ticketCommentRepository;
    private final ModelMapper modelMapper;

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
