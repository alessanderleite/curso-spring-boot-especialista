package com.example.app.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.domain.entity.ItemPedido;

public interface ItemsPedido extends JpaRepository<ItemPedido, Integer>{

}
