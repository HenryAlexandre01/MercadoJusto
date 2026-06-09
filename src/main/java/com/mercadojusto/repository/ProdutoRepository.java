package com.mercadojusto.repository;

import com.mercadojusto.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


//interface para operações de banco de dados envolvendo produtos
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    List<Produto> findByNomeContainingIgnoreCase(String nome);
    List<Produto> findByNomeContainingIgnoreCaseOrderByPrecoAsc(String nome);
    
    //consultas JPQL para cálculos estatísticos de preço baseados no nome do produto
    @Query("SELECT AVG(p.preco) FROM Produto p WHERE p.nome = :nome")
    Double calcularMediaPreco(@Param("nome") String nome);

    @Query("SELECT MIN(p.preco) FROM Produto p WHERE p.nome = :nome")
    Double encontrarMenorPreco(@Param("nome") String nome);

    @Query("SELECT MAX(p.preco) FROM Produto p WHERE p.nome = :nome")
    Double encontrarMaiorPreco(@Param("nome") String nome);

    //realiza uma consulta para identificar o nome da loja vinculada ao menor e maior preço de um produto
    @Query("SELECT p.estabelecimento.nomeLoja FROM Produto p WHERE p.nome = :nome ORDER BY p.preco ASC LIMIT 1")
    String findNomeLojaMenorPreco(@Param("nome") String nome);

    @Query("SELECT p.estabelecimento.nomeLoja FROM Produto p WHERE p.nome = :nome ORDER BY p.preco DESC LIMIT 1")
    String findNomeLojaMaiorPreco(@Param("nome") String nome);

    // No seu ProdutoRepository.java
    @Query("SELECT p FROM Produto p JOIN FETCH p.estabelecimento")
    List<Produto> findAllComEstabelecimento();
}