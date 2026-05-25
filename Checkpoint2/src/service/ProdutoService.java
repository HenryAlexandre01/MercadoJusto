package service;

import model.Produto;

public class ProdutoService {

    // Validação para verificar se o preço é maior que 0
    public boolean validarPreco(double preco) {
        if (preco > 0) {
            return true;
        } else {
            System.out.println("Erro: O preço deve ser maior que zero.");
            return false;
        }
    }

    // Método para formatar a exibição do produto 
    public String formatarProduto(Produto p) {
        return String.format("Item: %s | Categoria: %s | Preço: R$ %.2f", 
                             p.getNome(), 
                             p.getCategoria(), 
                             p.getPreco());
    }
}