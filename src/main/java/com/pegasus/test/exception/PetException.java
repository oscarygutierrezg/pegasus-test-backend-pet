package com.pegasus.test.exception;

public class PetException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PetException(String msg, Throwable e) {
		super(msg, e);
	}

}
