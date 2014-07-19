package com.factulab.exception;

import java.io.Serializable;

public class FactulabError implements Serializable {
	private static final long serialVersionUID = 1L;
	private String msgError;

	public String getMsgError() {
		return msgError;
	}

	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}

	@Override
	public String toString() {
		return "Error [msgError=" + msgError + "]";
	}

	public FactulabError(String msgError) {
		super();
		this.msgError = msgError;
	}

}
