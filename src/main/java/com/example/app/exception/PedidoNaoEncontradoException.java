package com.example.app.exception;

public class PedidoNaoEncontradoException extends RuntimeException {
	private static final long serialVersionUID = 3262864218758731109L;

	public PedidoNaoEncontradoException() {
		super("Pedido não encontrado");
	}

	
}
