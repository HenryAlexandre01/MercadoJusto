package com.mercadojusto.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;


//define a classe como entidade JPA vinculada à tabela 'produto'
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //validação, garante que o nome não seja nulo ou vazio
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    //validação, garante que um valaor minimo para o preço
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    private double preco;

    //garante que vários produtos podem pertencer a um estabelecimento
    @ManyToOne
    @JoinColumn(name = "estabelecimento_id")
    private Estabelecimento estabelecimento;

    private String nomeImagem;

    @Transient 
    private Double variacao;

   
    public Produto() {}
    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    // Getters e Setters para acesso aos campos encapsulados
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public String getNomeImagem() { return nomeImagem; }
    public void setNomeImagem(String nomeImagem) { this.nomeImagem = nomeImagem; }

    public Estabelecimento getEstabelecimento() { return estabelecimento; }
    public void setEstabelecimento(Estabelecimento estabelecimento) { this.estabelecimento = estabelecimento; }

    public void setVariacao(Double variacao) { this.variacao = variacao; }
    public Double getVariacao() { return variacao; }

    @Override
    public String toString() {
        return String.format("Produto: %s | Preço: R$ %.2f | Cat: %s", nome, preco);
    }
}