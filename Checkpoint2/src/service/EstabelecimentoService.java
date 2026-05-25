package service;
import model.Estabelecimento;
import model.Produto;
import java.util.ArrayList;

public class EstabelecimentoService {

    // Calcula a média de um produto em expecifico
    public double calcularMediaPorProduto(ArrayList<Estabelecimento> mercados, String nomeProduto) {
        double soma = 0;
        int contagem = 0;

        for (Estabelecimento e : mercados) {
            for (Produto p : e.getListaProdutos()) {
                if (p.getNome().equalsIgnoreCase(nomeProduto)) {
                    soma += p.getPreco();
                    contagem++;
                }
            }
        }

        return (contagem == 0) ? 0 : soma / contagem;
    }

    public void compararPrecos(ArrayList<Estabelecimento> mercados, String nomeProduto) {
        double menorPreco = Double.MAX_VALUE;
        Estabelecimento melhorLocal = null;

        for (Estabelecimento e : mercados) {
            for (Produto p : e.getListaProdutos()) {
                if (p.getNome().equalsIgnoreCase(nomeProduto) && p.getPreco() < menorPreco) {
                    menorPreco = p.getPreco();
                    melhorLocal = e;
                }
            }
        }

        if (melhorLocal != null) {
            System.out.println("\n--- MELHOR PREÇO ENCONTRADO ---");
            System.out.printf("Local: %s (%s)\nPreço: R$ %.2f\n", 
                melhorLocal.getNomeFantasia(), melhorLocal.getEndereco(), menorPreco);
        } else {
            System.out.println("Produto não encontrado.");
        }
    }
}