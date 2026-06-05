package service;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoBanco {
    // Endereço do HeidiSQL
    private static final String URL = "jdbc:mariadb://localhost:3306/mercado_justo";

    // Usuário e senha que você digita para abrir o HeidiSQL
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; 

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            return null;
        }
    }
}