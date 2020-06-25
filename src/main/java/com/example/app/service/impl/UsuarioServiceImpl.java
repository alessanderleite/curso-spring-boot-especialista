package com.example.app.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.domain.entity.ItemPedido;
import com.example.app.domain.entity.Pedido;
import com.example.app.domain.entity.Produto;
import com.example.app.domain.enums.StatusPedido;
import com.example.app.domain.repository.Clientes;
import com.example.app.domain.repository.ItemsPedido;
import com.example.app.domain.repository.Pedidos;
import com.example.app.domain.repository.Produtos;
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
	
	public Pedido salvar(PedidoDTO dto) {
		return null;
	}

	@Override
	public Optional<Pedido> obterPedidoCompleto(Integer id) {
		return repository.findByIdFetchItens(id);
	}

	@Override
	public void atualizaStatus(Integer id, StatusPedido statusPedido) {
		
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
