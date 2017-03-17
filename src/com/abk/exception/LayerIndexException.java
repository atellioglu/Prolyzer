package com.abk.exception;

public class LayerIndexException extends ProlyzerException {
	private static final long serialVersionUID = 1L;
	public LayerIndexException(int index){
		this.message = index+" layer not found in model";
	}
	public LayerIndexException(int index,String message){
		this.message = String.format(message, index);
	}
	public LayerIndexException(String message,int index){
		this.message = String.format(message, index);
	}
}
