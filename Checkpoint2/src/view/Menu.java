package view;

import java.util.ArrayList;
import java.util.Scanner;
import model.Estabelecimento;
import model.Produto;
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
            // Exibe os 6 mercados para o usuário
            System.out.println((i + 1) + " - " + rede.get(i).getNomeFantasia());
        }
        
        System.out.print("Escolha: ");
        int escolhaUsuario = scanner.nextInt();
        scanner.nextLine();

        int indexReal = escolhaUsuario - 1;

        if (indexReal >= 0 && indexReal < rede.size()) {
            System.out.print("Nome do Produto: "); 
            String nome = scanner.nextLine();
            
            System.out.print("Preço: "); 
            double preco = scanner.nextDouble();
            scanner.nextLine();

            rede.get(indexReal).getListaProdutos().add(new Produto(nome, preco, "Geral"));
            System.out.println("Cadastrado com sucesso no " + rede.get(indexReal).getNomeFantasia() + "!");
        } else {
            System.out.println("Opção de mercado inválida!");
        }
    }

    private void comparar() {
        System.out.print("Produto para busca: ");
        String busca = scanner.nextLine();
        service.compararPrecos(rede, busca);
    }

    private void verMedias() {
        System.out.print("Digite o nome do produto para ver a média regional: ");
        String busca = scanner.nextLine();
        
        double media = service.calcularMediaPorProduto(rede, busca);
        
        if (media > 0) {
            System.out.printf("\nA média de preço para '%s' na região é: R$ %.2f\n", busca, media);
            System.out.println("Use esse valor para identificar se um mercado está cobrando um preço justo.");
        } else {
            System.out.println("Nenhum dado encontrado para este produto.");
        }
    }

    public static void main(String[] args) {
        new Menu().iniciar();
    }
}