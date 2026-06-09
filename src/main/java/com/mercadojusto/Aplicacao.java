package com.mercadojusto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Aplicacao {

    //metodo main, inicializa o contexto do Spring e roda o servidor embutido (Tomcat)
    public static void main(String[] args) {
        SpringApplication.run(Aplicacao.class, args);

        //mensagem no console de que o servidor foi iniciado
        System.out.println("Servidor iniciado com sucesso, http://localhost:8080");
    }
}

