package com.example.app.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.domain.entity.Produto;

public interface Produtos extends JpaRepository<Produto, Integer> {

}
