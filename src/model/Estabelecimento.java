package model;
import java.util.ArrayList;

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
        super(nomeFantasia, bairro);
    }

    /**
     * Mercados populares apoiam a comunidade local, portanto não aplicam taxas adicionais.
     */
    @Override
    public double calcularPrecoComTaxa(double preco) {
        return preco; // Mantém o preço justo original
    }
}

public static class EstabelecimentoCentral extends Estabelecimento {
    // Construtor repassando os dados para a classe mãe
    public EstabelecimentoCentral(String nomeFantasia, String bairro) {
        super(nomeFantasia, bairro);
    }

    /**
     * Estabelecimentos centrais/ricos embutem taxas operacionais ou de conveniência de 5% 
     */
    @Override
    public double calcularPrecoComTaxa(double preco) {
        return preco * 1.05; // Adiciona taxa de conveniência/logística de 5%
    }
}
    
}