package com.example.app.rest.controller;

import static org.springframework.http.HttpStatus.*;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.app.domain.entity.ItemPedido;
import com.example.app.domain.entity.Pedido;
import com.example.app.domain.enums.StatusPedido;
import com.example.app.rest.dto.AtualizacaoStatusPedidoDTO;
import com.example.app.rest.dto.InformacaoItemPedidoDTO;
import com.example.app.rest.dto.InformacoesPedidoDTO;
import com.example.app.rest.dto.PedidoDTO;
import com.example.app.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService service;
	
	@PostMapping
	@ResponseStatus(CREATED)
	public Integer save(@RequestBody @Valid PedidoDTO dto) {
		Pedido pedido = service.salvar(dto);
		return pedido.getId();
	}
	
	@GetMapping("/{id}")
	public InformacoesPedidoDTO getById(@PathVariable Integer id) {
		return service
				.obterPedidoCompleto(id)
				.map(p -> converter(p))
				.orElseThrow(() ->
						new ResponseStatusException(NOT_FOUND, "Pedido não encontrado."));
				
	}
	
	@PatchMapping("/{id}")
	@ResponseStatus(NO_CONTENT)
	public void updateStatus(@PathVariable Integer id,
							 @RequestBody AtualizacaoStatusPedidoDTO dto) {
		String novoStatus = dto.getNovoStatus();
		service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
	}
	
	private InformacoesPedidoDTO converter(Pedido pedido) {
		InformacoesPedidoDTO info = new InformacoesPedidoDTO();
		info.setCodigo(pedido.getId());
		info.setCpf(pedido.getCliente().getCpf());
		info.setNomeCliente(pedido.getCliente().getNome());
		info.setTotal(pedido.getTotal());
		info.setDataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		info.setStatus(pedido.getStatus().name());
		info.setItems(converter(pedido.getItens()));
		return info;
	}
	
	public List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens) {
		if (CollectionUtils.isEmpty(itens)) {
			return Collections.emptyList();
		}
		return itens.stream().map(item -> {
			InformacaoItemPedidoDTO infoPedido = new InformacaoItemPedidoDTO(
					item.getProduto().getDescricao(), 
					item.getProduto().getPreco(),
					item.getQuantidade());
					return infoPedido;
		}).collect(Collectors.toList());
		
	}
}
