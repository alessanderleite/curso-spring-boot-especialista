package com.example.app.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.app.domain.enums.StatusPedido;

@Entity
@Table(name = "pedido")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@Column(name = "data_pedido")
	private LocalDate dataPedido;
	
	@Column(name = "total", precision = 20, scale = 2)
	private BigDecimal total;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusPedido status;
	
	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itens;

	public Pedido() {
	}

	public Pedido(Integer id, Cliente cliente, LocalDate dataPedido, BigDecimal total, StatusPedido status,
			List<ItemPedido> itens) {
		this.id = id;
		this.cliente = cliente;
		this.dataPedido = dataPedido;
		this.total = total;
		this.status = status;
		this.itens = itens;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public LocalDate getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDate dataPedido) {
		this.dataPedido = dataPedido;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", cliente=" + cliente + ", dataPedido=" + dataPedido + ", total=" + total
				+ ", status=" + status + ", itens=" + itens + "]";
	}
	
	
}
