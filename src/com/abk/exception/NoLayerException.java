package com.abk.exception;

public class NoLayerException extends ProlyzerException {
	private static final long serialVersionUID = 1L;
	public NoLayerException() {
		this.message = "Layer not found in model";
	}
}
