package com.supportsystem.application.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.supportsystem.application.response.dtos.TicketResponseDTO;
import com.supportsystem.application.request.dtos.TicketRequestDTO;
import com.supportsystem.application.services.TicketService;
import com.supportsystem.application.shared.Status;
import com.supportsystem.application.shared.Status.Resolution;
import com.supportsystem.application.shared.Status.Ticket;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

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
        log.info("get a ticket by Id");
        TicketResponseDTO response = ticketService.getTicketById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    @Description(value = "save a new ticket")
    public @ResponseBody ResponseEntity<TicketResponseDTO> saveTicket(@RequestBody final TicketRequestDTO ticket) {
        log.info("save a new ticket", ticket);
        TicketResponseDTO response = ticketService.save(ticket);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
