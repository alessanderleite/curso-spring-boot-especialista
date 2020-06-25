package com.example.app.domain.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "produto")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "descricao")
	@NotEmpty(message = "{campo.descricao.obrigatorio}")
	private String descricao;
	
	@Column(name = "preco_unitario")
	@NotNull(message = "{campo.preco.obrigatorio}")
	private BigDecimal preco;

	public Produto() {
	}

	public Produto(Integer id, @NotEmpty(message = "{campo.descricao.obrigatorio}") String descricao,
			@NotNull(message = "{campo.preco.obrigatorio}") BigDecimal preco) {
		this.id = id;
		this.descricao = descricao;
		this.preco = preco;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", descricao=" + descricao + ", preco=" + preco + "]";
	}
	
	
}
