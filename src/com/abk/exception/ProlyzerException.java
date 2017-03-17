package com.abk.exception;

public class ProlyzerException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	protected String message;
	public String getMessage(){
		return this.message;
	}
}
