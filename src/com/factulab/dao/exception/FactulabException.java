package com.factulab.dao.exception;


public class FactulabException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FactulabException() {
		super();
	}

	public FactulabException(String message) {
		super(message);
	}

	public FactulabException(String message, Throwable cause) {
		super(message, cause);
	}

	public FactulabException(Throwable cause) {
		super(cause);
	}
	
}
