package com.orastays.smsserver.exceptions;

public class SMSSendException extends Exception {

	private static final long serialVersionUID = 8122816820501266175L;
	private String name;

	public SMSSendException(String name) {
		super(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}