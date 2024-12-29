package com.supportsystem.application.shared;

public class Status {

	public enum Ticket {
		OPEN, NEW, CLOSED, ESCALATE
	}
	
	public enum Resolution {
		UNRESOLVED, RESOLVED, WONT_FIX, DUPLICATE
	}

}
