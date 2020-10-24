package com.supportsystem.application.controllers;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

}
