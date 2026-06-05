package service;

import model.Estabelecimento;
import model.Produto;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EstabelecimentoService {


    public double buscarPrecoAtualNoBanco(int idEstabelecimento, String nomeProduto) {
    String sql = "SELECT preco FROM produto WHERE estabelecimento_id = ? AND LOWER(nome) = LOWER(?)";
    
    try (Connection conn = ConexaoBanco.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, idEstabelecimento);
        stmt.setString(2, nomeProduto);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("preco"); // Retorna o preço encontrado
            }
        }
    } catch (SQLException e) {
        System.out.println("Erro ao buscar preço atual: " + e.getMessage());
    }
    return -1; // Retorna -1 se o produto não existir nesse mercado
}
    // Método responsável por atualizar o preço de um produto existente
    public void atualizarPrecoNoBanco(int idEstabelecimento, String nomeProduto, double novoPreco) {
        String sql = "UPDATE produto SET preco = ? WHERE estabelecimento_id = ? AND LOWER(nome) = LOWER(?)";
        
        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDouble(1, novoPreco);
            stmt.setInt(2, idEstabelecimento);
            stmt.setString(3, nomeProduto);
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("Sucesso: Preço atualizado!");
            } else {
                System.out.println("Aviso: Nenhum produto com esse nome foi encontrado neste mercado.");
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar preço no banco: " + e.getMessage());
        }
    }

    // Método responsável por salvar um novo estabelecimento (Opção 4)
    public void salvarEstabelecimentoNoBanco(String nome, String bairro, int tipo) {
        String sql = "INSERT INTO estabelecimento (nome_fantasia, endereco) VALUES (?, ?)";
        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, bairro);
            stmt.executeUpdate();
            System.out.println("Sucesso: Novo estabelecimento cadastrado!");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar estabelecimento no banco: " + e.getMessage());
        }
    }

    // Método responsável por salvar novos produtos (Opção 1)
    public void salvarProdutoNoBanco(String nome, double preco, String categoria, int idEstabelecimento) {
        String sql = "INSERT INTO produto (nome, preco, categoria, estabelecimento_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setDouble(2, preco);
            stmt.setString(3, categoria);
            stmt.setInt(4, idEstabelecimento);
            stmt.executeUpdate();
            System.out.println("Sucesso: Salvo no banco de dados");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar no banco: " + e.getMessage());
        }
    }

    public void deletarProdutoNoBanco(int idEstabelecimento, String nomeProduto) {
    // SQL que deleta o produto filtrando pelo mercado e pelo nome exato
    String sql = "DELETE FROM produto WHERE estabelecimento_id = ? AND LOWER(nome) = LOWER(?)";
    
    try (Connection conn = ConexaoBanco.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, idEstabelecimento);
        stmt.setString(2, nomeProduto);
        
        int linhasAfetadas = stmt.executeUpdate();
        
        if (linhasAfetadas > 0) {
            System.out.println("Sucesso: Produto excluído com sucesso!");
        } else {
            System.out.println("Aviso: Nenhum produto com esse nome foi encontrado neste mercado para ser excluído.");
        }
        
    } catch (SQLException e) {
        System.out.println("Erro ao deletar produto no banco: " + e.getMessage());
    }
}

    // Método responsável por exibir médias regionais e o melhor preço (Opções 2 e 3)
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