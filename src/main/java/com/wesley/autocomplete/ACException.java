package com.wesley.autocomplete;

public class ACException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public ACException(String message) {
	  super(message);
	}
	
	public ACException(String message, Throwable cause) {
	  super(message, cause);
	}
	
	public ACException(Throwable t) {
	  super(t);
	}
}
