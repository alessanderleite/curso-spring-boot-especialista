package com.example.app.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.entity.Cliente;
import com.example.app.domain.entity.ItemPedido;
import com.example.app.domain.entity.Pedido;
import com.example.app.domain.entity.Produto;
import com.example.app.domain.enums.StatusPedido;
import com.example.app.domain.repository.Clientes;
import com.example.app.domain.repository.ItemsPedido;
import com.example.app.domain.repository.Pedidos;
import com.example.app.domain.repository.Produtos;
import com.example.app.exception.PedidoNaoEncontradoException;
import com.example.app.exception.RegraNegocioException;
import com.example.app.rest.dto.ItemPedidoDTO;
import com.example.app.rest.dto.PedidoDTO;
import com.example.app.service.PedidoService;

@Service
public class UsuarioServiceImpl implements PedidoService {
	
	@Autowired
	private Pedidos repository;
	
	@Autowired
	private Clientes clientesRepository;
	
	@Autowired
	private Produtos produtosRepository;
	
	@Autowired
	private ItemsPedido itemsPedidoRepository;

	@Override
	@Transactional
	public Pedido salvar(PedidoDTO dto) {
		Integer idCliente = dto.getCliente();
		Cliente cliente = clientesRepository
				.findById(idCliente)
				.orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));
		
		Pedido pedido = new Pedido();
		pedido.setTotal(dto.getTotal());
		pedido.setDataPedido(LocalDate.now());
		pedido.setCliente(cliente);
		pedido.setStatus(StatusPedido.REALIZADO);
		
		List<ItemPedido> itemsPedidos = converterItems(pedido, dto.getItems());
		repository.save(pedido);
		itemsPedidoRepository.saveAll(itemsPedidos);
		pedido.setItens(itemsPedidos);
		return pedido;
	}

	@Override
	public Optional<Pedido> obterPedidoCompleto(Integer id) {
		return repository.findByIdFetchItens(id);
	}

	@Override
	@Transactional
	public void atualizaStatus(Integer id, StatusPedido statusPedido) {
		repository
				.findById(id)
				.map(pedido -> {
					pedido.setStatus(statusPedido);
					return repository.save(pedido);
				}).orElseThrow(() -> new PedidoNaoEncontradoException());
	}

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraNegocioException(
                                            "Código de produto inválido: "+ idProduto
                                    ));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());

    }
    
}
