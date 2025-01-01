package com.supportsystem.application.exceptions;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public UserNotFoundException(Object identifier) {
        super("Could not find user " + identifier);
    }
}
