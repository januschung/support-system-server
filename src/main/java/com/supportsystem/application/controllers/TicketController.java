package com.supportsystem.application.controllers;

import java.util.List;

import com.supportsystem.application.response.dtos.TicketCommentResponseDTO;
import com.supportsystem.application.services.TicketCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.supportsystem.application.response.dtos.TicketResponseDTO;
import com.supportsystem.application.request.dtos.TicketRequestDTO;
import com.supportsystem.application.services.TicketService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketCommentService ticketCommentService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Description(value = "returns all tickets")
    public @ResponseBody ResponseEntity<List<TicketResponseDTO>> getAllTickets() {
        log.info("get all tickets");
        List<TicketResponseDTO> response = ticketService.getAllTickets();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Description(value = "returns a ticket by Id")
    public @ResponseBody ResponseEntity<TicketResponseDTO> getTicketById(@PathVariable Long id) {
        log.info("get a ticket by Id " + id);
        TicketResponseDTO response = ticketService.getTicketById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    @Description(value = "save a new ticket")
    public @ResponseBody ResponseEntity<TicketResponseDTO> createTicket(@RequestBody final TicketRequestDTO ticket) {
        log.info("create a new ticket " + ticket);
        TicketResponseDTO response = ticketService.createTicket(ticket);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(path = "{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
    @Description(value = "update an existing ticket")
    public @ResponseBody ResponseEntity<TicketResponseDTO> updateTicket(
        @PathVariable Long id, @RequestBody final TicketRequestDTO ticket) {
        log.info("update ticket with ID " + id);
        TicketResponseDTO response = ticketService.updateTicket(id, ticket);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Description("Deletes a ticket by its ID")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        log.info("Deleting ticket with ID: {}", id);
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{ticketId}/comments")
    @Description(value = "Get all comments for a ticket")
    public ResponseEntity<List<TicketCommentResponseDTO>> getAllCommentsByTicketId(@PathVariable Long ticketId) {
        List<TicketCommentResponseDTO> comments = ticketCommentService.getAllTicketCommentsByTicketId(ticketId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/{ticketId}/comments/{commentId}")
    @Description(value = "Get a single comment by ticket and comment ID")
    public ResponseEntity<TicketCommentResponseDTO> getCommentByTicketIdAndCommentId(
        @PathVariable Long ticketId,
        @PathVariable Long commentId) {
        TicketCommentResponseDTO comment = ticketCommentService.getCommentByTicketIdAndCommentId(ticketId, commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

}
