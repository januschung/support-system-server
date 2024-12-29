package com.supportsystem.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TicketCommentNotFoundException extends RuntimeException {

	@Serial
    private static final long serialVersionUID = 1L;

    public TicketCommentNotFoundException(Long ticketId) {
        super("Could not find comment for ticket " + ticketId);
    }

    public TicketCommentNotFoundException(Long ticketId, Long commentId) {
        super("Could not find comment " + commentId + " for ticket " + ticketId);
    }

}
