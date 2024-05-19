package com.supportsystem.application.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supportsystem.application.domains.TicketComment;
import com.supportsystem.application.exceptions.TicketNotFoundException;
import com.supportsystem.application.repositories.TicketCommentRepository;
import com.supportsystem.application.request.dtos.TicketCommentRequestDTO;
import com.supportsystem.application.response.dtos.TicketCommentResponseDTO;

@Service
public class TicketCommentServiceImpl implements TicketCommentService {

	@Autowired
	private TicketCommentRepository ticketCommentRepository;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public List<TicketCommentResponseDTO> getAllTicketComments() {
		return ticketCommentRepository.findAll().stream().map(ticketComment -> {
			return modelMapper.map(ticketComment, TicketCommentResponseDTO.class);
		}).collect(Collectors.toList());
	}

	@Override
	public TicketCommentResponseDTO getTicketCommentById(Long id) {
		return ticketCommentRepository.findById(id).map(ticketComment -> {
			return modelMapper.map(ticketComment, TicketCommentResponseDTO.class);
		}).orElseThrow(() -> new TicketNotFoundException(id));
	}

	@Override
	public List<TicketCommentResponseDTO> getAllTicketCommentsByTicketId(Long id) {
		return ticketCommentRepository.findAllByTicketId(id).stream().map(ticketComment -> {
			return modelMapper.map(ticketComment, TicketCommentResponseDTO.class);
		}).collect(Collectors.toList());
	}

	@Override
	public TicketCommentResponseDTO save(TicketCommentRequestDTO ticketCommentRequestDTO) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		TicketComment entity = modelMapper.map(ticketCommentRequestDTO, TicketComment.class);
		ticketCommentRepository.save(entity);
		return modelMapper.map(entity, TicketCommentResponseDTO.class);
	}

}
