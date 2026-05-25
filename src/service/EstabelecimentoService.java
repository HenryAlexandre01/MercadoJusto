package service;
import model.Estabelecimento;
import model.Produto;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EstabelecimentoService {

    // Garante que o método de salvar está aqui...
    public void salvarProdutoNoBanco(String nome, double preco, String categoria, int idEstabelecimento) {
        String sql = "INSERT INTO produto (nome, preco, categoria, estabelecimento_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setDouble(2, preco);
            stmt.setString(3, categoria);
            stmt.setInt(4, idEstabelecimento);
            stmt.executeUpdate();
            System.out.println("Sucesso: Salvo no banco de dados do HeidiSQL!");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar no banco: " + e.getMessage());
        }
    }

    public void exibirMediaEMelhorPrecoNoBanco(String nomeProduto) {
        String sqlMedia = "SELECT AVG(preco) as media_preco FROM produto WHERE LOWER(nome) = LOWER(?)";
        
        String sqlMelhorPreco = "SELECT p.preco, e.nome_fantasia, e.endereco FROM produto p " +
                                "JOIN estabelecimento e ON p.estabelecimento_id = e.id " +
                                "WHERE LOWER(p.nome) = LOWER(?) " +
                                "ORDER BY p.preco ASC LIMIT 1";

        try (Connection conn = ConexaoBanco.conectar()) {
            
            double media = 0;
            try (PreparedStatement stmtMedia = conn.prepareStatement(sqlMedia)) {
                stmtMedia.setString(1, nomeProduto);
                try (ResultSet rsMedia = stmtMedia.executeQuery()) {
                    if (rsMedia.next()) {
                        media = rsMedia.getDouble("media_preco");
                    }
                }
            }

            if (media == 0) {
                System.out.println("Nenhum dado encontrado para este produto no banco de dados.");
                return;
            }

            System.out.printf("\nA média de preço para '%s' na região é: R$ %.2f\n", nomeProduto, media);
            System.out.println("Use esse valor para identificar se um mercado está cobrando um preço justo.");

            try (PreparedStatement stmtMelhor = conn.prepareStatement(sqlMelhorPreco)) {
                stmtMelhor.setString(1, nomeProduto);
                try (ResultSet rsMelhor = stmtMelhor.executeQuery()) {
                    if (rsMelhor.next()) {
                        double menorPreco = rsMelhor.getDouble("preco");
                        String local = rsMelhor.getString("nome_fantasia");
                        String endereco = rsMelhor.getString("endereco");

                        System.out.println("\n--- MELHOR PREÇO ENCONTRADO ---");
                        System.out.printf("Local: %s (%s)\nPreço: R$ %.2f\n", local, endereco, menorPreco);
                        
                        if (menorPreco < media) {
                            double economy = media - menorPreco;
                            System.out.printf("Economia de R$ %.2f em relação à média regional!\n", economy);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar dados no banco: " + e.getMessage());
        }
    }
}