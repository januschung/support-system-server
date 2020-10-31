package com.supportsystem.application.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.supportsystem.application.domains.Ticket;
import com.supportsystem.application.services.TicketService;

import ch.qos.logback.classic.Logger;

@RestController
@RequestMapping("/tickets")
public class TicketController {
	
	protected Logger logger = (Logger) LoggerFactory.getLogger(TicketController.class);
	
    @Autowired
    private TicketService ticketService;
    
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    @Description(value = "returns all tickets")
    public @ResponseBody ResponseEntity<List<Ticket>> getAllTickets() {
    	logger.info("get all tickets");
    	List<Ticket> response = ticketService.getAllTickets();
    	return new ResponseEntity<List<Ticket>>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @Description(value = "returns a ticket by Id")
    public @ResponseBody ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        logger.info("get a ticket by Id");
        Optional<Ticket> response = ticketService.getTicketById(id);
        return new ResponseEntity(response, HttpStatus.OK);
    }

//    @RequestMapping(value = {""}, method = RequestMethod.POST)
//    @Description(value = "update / save ticket ")
//    public
//    @ResponseBody
//    ResponseEntity save(@RequestBody @Valid final TicketDTO ticket) {
//        logger.info("Update ticket", value("body", ticket));
//        ticket.setLastModified(new Date());
//        // TODO: Change it to current user
//        ticket.setModifiedBy(1L);
//        response = ticketService.save(ticket);
//        return new ResponseEntity(ticket, HttpStatus.OK);
//    }
}
