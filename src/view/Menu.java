package view;

import java.util.ArrayList;
import java.util.Scanner;
import model.Estabelecimento;
import model.Produto;
import service.ConexaoBanco;
import service.EstabelecimentoService;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private EstabelecimentoService service = new EstabelecimentoService();
    private ArrayList<Estabelecimento> rede = new ArrayList<>();

    public void iniciar() {
        // Lista de mercados
        rede.add(new Estabelecimento("Popular - São João", "Bairro Norte"));
        rede.add(new Estabelecimento("Popular - Seu zé", "Bairro Sul"));
        rede.add(new Estabelecimento("Popular - Quitandinnha da Esquina", "Bairro Leste"));
        rede.add(new Estabelecimento("Central - Dia", "Centro"));
        rede.add(new Estabelecimento("Central - St marche", "Avenida Principal"));
        rede.add(new Estabelecimento("Central - Empório Santa Maria", "Terminal"));

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n=== MERCADO JUSTO ===");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Melhor Preço");
            System.out.println("3. Ver Médias");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1: cadastrar(); break;
                case 2: comparar(); break;
                case 3: verMedias(); break;
            }
        }
    }

    private void cadastrar() {
    System.out.println("\nSelecione o mercado (1 a 6):");
    for (int i = 0; i < rede.size(); i++) {
        System.out.println((i + 1) + " - " + rede.get(i).getNomeFantasia());
    }
    
    System.out.print("Escolha: ");
    int escolhaUsuario = scanner.nextInt();
    scanner.nextLine();

    // O ID do mercado no banco vai de 1 a 6
    if (escolhaUsuario >= 1 && escolhaUsuario <= 6) {
        System.out.print("Nome do Produto: "); 
        String nome = scanner.nextLine();
        
        System.out.print("Preço: "); 
        double preco = scanner.nextDouble();
        scanner.nextLine();

        // Faz a validação de preço que criou no ProdutoService
        service.ProdutoService prodService = new service.ProdutoService();
        if (prodService.validarPreco(preco)) {
            
            // 2. Chama o método que joga direto no Banco de Dados!
            service.salvarProdutoNoBanco(nome, preco, "Geral", escolhaUsuario);
            
        }
    } else {
        System.out.println("Opção de mercado inválida!");
    }
}

    private void comparar() {
    System.out.print("Produto para busca: ");
    String busca = scanner.nextLine();
    service.exibirMediaEMelhorPrecoNoBanco(busca);
}

   private void verMedias() {
    System.out.print("Digite o nome do produto para ver a média regional e o melhor local: ");
    String busca = scanner.nextLine();
    
    service.exibirMediaEMelhorPrecoNoBanco(busca);
}

   public static void main(String[] args) {
    System.out.println("Testando a ponte com o banco...");
    
    java.sql.Connection conexao = ConexaoBanco.conectar();
    
    if (conexao != null) {
        System.out.println("Deu certo! O Java se conectou ao HeidiSQL!");
    } else {
        System.out.println("A conexão falhou. Verifique se a senha ou a porta 3306 estão certas.");
    }
    new Menu().iniciar();
    }
}