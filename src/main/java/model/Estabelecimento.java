package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import dao.ConexaoBanco;

/*
* Classe mãe
*Aplica a ideia de Encapsulamento com atributos private, getter, setters
*/
public class Estabelecimento {
    private String nomeFantasia;
    private String bairro;

    // Construtor padrão
    public Estabelecimento(String nomeFantasia, String bairro) {
        this.nomeFantasia = nomeFantasia;
        this.bairro = bairro;
    }

    // Métodos Getters e Setters
    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    /**
     * Por padrão, um estabelecimento genérico não altera o preço base do produto
     */
    public double calcularPrecoComTaxa(double preco) {
        return preco;
    }

public static class EstabelecimentoPopular extends Estabelecimento {
    public EstabelecimentoPopular(String nomeFantasia, String bairro) {
        super(nomeFantasia.startsWith("Popular -") ? nomeFantasia : "Popular - " + nomeFantasia, bairro);
    }

    @Override
    public double calcularPrecoComTaxa(double preco) {
        return preco; 
    }
}

public static class EstabelecimentoCentral extends Estabelecimento {
    public EstabelecimentoCentral(String nomeFantasia, String bairro) {
        super(nomeFantasia.startsWith("Central -") ? nomeFantasia : "Central - " + nomeFantasia, bairro);
    }

    @Override
    public double calcularPrecoComTaxa(double preco) {
        return preco; 
    }
}

/*ADICIONANDO MÉTODO PARA SALVAR ESTABELECIMENTOS NOVOS */
public void salvarEstabelecimentoNoBanco(String nome, String bairro, int tipo) {
   
    String sql = "INSERT INTO estabelecimento (nome_fantasia, endereco) VALUES (?, ?)";
    
    try (Connection conn = ConexaoBanco.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, nome);
        stmt.setString(2, bairro);
        stmt.executeUpdate();
        
        System.out.println("Sucesso: Novo estabelecimento cadastrado no HeidiSQL!");
        
        
    } catch (SQLException e) {
        System.out.println("Erro ao salvar estabelecimento no banco: " + e.getMessage());
    }
}

public void atualizarPrecoNoBanco(int idEstabelecimento, String nomeProduto, double novoPreco) {
    // SQL que atualiza o preço filtrando pelo ID do mercado e pelo nome do produto
    String sql = "UPDATE produto SET preco = ? WHERE estabelecimento_id = ? AND LOWER(nome) = LOWER(?)";
    
    try (Connection conn = ConexaoBanco.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setDouble(1, novoPreco);
        stmt.setInt(2, idEstabelecimento);
        stmt.setString(3, nomeProduto);
        
        int linhasAfetadas = stmt.executeUpdate();
        
        if (linhasAfetadas > 0) {
            System.out.println("Sucesso: Preço atualizado com sucesso no HeidiSQL!");
        } else {
            System.out.println("Aviso: Nenhum produto com esse nome foi encontrado neste mercado.");
        }
        
    } catch (SQLException e) {
        System.out.println("Erro ao atualizar preço no banco: " + e.getMessage());
    }
}
    
}