package com.example.app.service;

import java.util.Optional;

import com.example.app.domain.entity.Pedido;
import com.example.app.domain.enums.StatusPedido;
import com.example.app.rest.dto.PedidoDTO;

public interface PedidoService {
	
	Pedido salvar(PedidoDTO dto);
	Optional<Pedido> obterPedidoCompleto(Integer id);
	void atualizaStatus(Integer id, StatusPedido statusPedido);
}
