package com.example.app.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.domain.entity.Pedido;

public interface Pedidos extends JpaRepository<Pedido, Integer> {

}
