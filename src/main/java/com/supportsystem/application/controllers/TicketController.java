package com.supportsystem.application.controllers;

import static net.logstash.logback.argument.StructuredArguments.value;

import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.supportsystem.application.response.dtos.TicketDTO;
import com.supportsystem.application.services.TicketService;

import ch.qos.logback.classic.Logger;

@RestController
@RequestMapping("/tickets")
public class TicketController {

	protected Logger logger = (Logger) LoggerFactory.getLogger(TicketController.class);

	@Autowired
	private TicketService ticketService;

	@RequestMapping(value = { "" }, method = RequestMethod.GET)
	@Description(value = "returns all tickets")
	public @ResponseBody ResponseEntity<List<TicketDTO>> getAllTickets() {
		logger.info("get all tickets");
		List<TicketDTO> response = ticketService.getAllTickets();
		return new ResponseEntity<List<TicketDTO>>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@Description(value = "returns a ticket by Id")
	public @ResponseBody ResponseEntity<TicketDTO> getTicketById(@PathVariable Long id) {
		logger.info("get a ticket by Id");
		TicketDTO response = ticketService.getTicketById(id);
		return new ResponseEntity<TicketDTO>(response, HttpStatus.OK);
	}

    @RequestMapping(value = {""}, method = RequestMethod.POST)
    @Description(value = "save a new ticket")
    public
    @ResponseBody
    ResponseEntity<TicketDTO> save(@RequestBody final com.supportsystem.application.request.dtos.TicketDTO ticket) {
        logger.info("save a new ticket", value("body", ticket));
        ticket.setLastModified(new Date());
        ticket.setModifiedBy(1L);
        TicketDTO response = ticketService.save(ticket);
        return new ResponseEntity<TicketDTO>(response, HttpStatus.OK);
    }
    
}
