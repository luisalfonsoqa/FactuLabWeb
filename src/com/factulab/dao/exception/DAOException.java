package com.factulab.dao.exception;


public class DAOException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DAOException() {
		super();
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(String query, String message, Throwable cause) {
		super("Error["+message+"]" + (query == null ? "" : "Query["+query +"]"), cause);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}
}
