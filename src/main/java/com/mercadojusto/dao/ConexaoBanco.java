package com.mercadojusto.dao;

import java.sql.Connection;
import java.sql.DriverManager;


//classe responsavel por estabelecer a conexão manual com o banco de dados SQL
public class ConexaoBanco {
    // Endereço do HeidiSQL
    private static final String URL = "jdbc:mysql://localhost:3306/mercado_justo";
    
    // Usuário e senha que você digita para abrir o HeidSql
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; 


    //retorna uma mensagem de conexão se bem-sucedido, ou null caso de erro
    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            return null;
        }
    }
}