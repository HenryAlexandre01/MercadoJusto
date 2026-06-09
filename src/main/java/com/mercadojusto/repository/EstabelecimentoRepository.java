package com.mercadojusto.repository;

import com.mercadojusto.model.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//interface que fornece métodos prontos para manipular dados de estabelecimentos
@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Integer> {
}