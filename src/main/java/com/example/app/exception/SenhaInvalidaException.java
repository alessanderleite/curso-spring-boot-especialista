package com.example.app.exception;

public class SenhaInvalidaException extends RuntimeException {
	private static final long serialVersionUID = 4785764651196179602L;

	public SenhaInvalidaException() {
		super("Senha inválida");
	}

	
}
