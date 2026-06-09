package com.mercadojusto.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

//marca a classe como uma entidade de banco de dados
@Entity
@Data
@NoArgsConstructor
public class Estabelecimento {
//define a chave primária com ID autoincrementados
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //dados da loja
    private String nomeLoja;
    private String bairro;
    private String endereco;
    private String tipo;

    public Estabelecimento(String nomeLoja, String bairro, String tipo) {
        this.nomeLoja = nomeLoja;
        this.bairro = bairro;
        this.tipo = tipo;
    }
}