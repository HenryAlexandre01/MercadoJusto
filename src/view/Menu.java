package view;

import java.util.ArrayList;
import java.util.Scanner;
import model.Estabelecimento;
import model.Estabelecimento.EstabelecimentoCentral;
import model.Estabelecimento.EstabelecimentoPopular;
import service.ConexaoBanco;
import service.EstabelecimentoService;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private EstabelecimentoService service = new EstabelecimentoService();
    private ArrayList<Estabelecimento> rede = new ArrayList<>();

    public void iniciar() {
        // Lista de Mercados Iniciais
        rede.add(new EstabelecimentoPopular("São João", "Bairro Norte"));
        rede.add(new EstabelecimentoPopular("Seu zé", "Bairro Sul"));
        rede.add(new EstabelecimentoPopular("Quitandinnha da Esquina", "Bairro Leste"));
        
        rede.add(new EstabelecimentoCentral("Dia", "Centro"));
        rede.add(new EstabelecimentoCentral("St marche", "Avenida Principal"));
        rede.add(new EstabelecimentoCentral("Empório Santa Maria", "Terminal"));

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n=== MERCADO JUSTO ===");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Melhor Preço");
            System.out.println("3. Ver Médias");
            System.out.println("4. Cadastrar Novo Estabelecimento");
            System.out.println("5. Atualizar Preço de Produto");
            System.out.println("6. Deletar Produto");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1: cadastrar(); break;
                case 2: comparar(); break;
                case 3: verMedias(); break;
                case 4: cadastrarEstabelecimento(); break;
                case 5: atualizarPrecoProduto(); break;
                case 6: deletarProduto(); break;
                default: if(opcao != 0) System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrar() {
        System.out.println("\nSelecione o mercado:");
        for (int i = 0; i < rede.size(); i++) {
            System.out.println((i + 1) + " - " + rede.get(i).getNomeFantasia());
        }
        
        System.out.print("Escolha: ");
        int escolhaUsuario = scanner.nextInt();
        scanner.nextLine();

        if (escolhaUsuario >= 1 && escolhaUsuario <= rede.size()) {
            System.out.print("Nome do Produto: "); 
            String nome = scanner.nextLine();
            
            System.out.print("Preço: "); 
            double preco = scanner.nextDouble();
            scanner.nextLine();

            service.ProdutoService prodService = new service.ProdutoService();
            if (prodService.validarPreco(preco)) {
                service.salvarProdutoNoBanco(nome, preco, "Geral", escolhaUsuario);
            }
        } else {
            System.out.println("Opção de mercado inválida!");
        }
    }

    private void atualizarPrecoProduto() {
        System.out.println("\nSelecione o mercado onde o produto está cadastrado:");
        for (int i = 0; i < rede.size(); i++) {
            System.out.println((i + 1) + " - " + rede.get(i).getNomeFantasia());
        }
        
        System.out.print("Escolha o mercado: ");
        int escolhaUsuario = scanner.nextInt();
        scanner.nextLine(); 

        if (escolhaUsuario >= 1 && escolhaUsuario <= rede.size()) {
            System.out.print("Digite o nome exato do produto que deseja atualizar: ");
            String nomeProduto = scanner.nextLine();
            
            double precoAtual = service.buscarPrecoAtualNoBanco(escolhaUsuario, nomeProduto);
            
            if (precoAtual != -1) {
                System.out.printf("O preço atual deste produto neste mercado é: R$ %.2f\n", precoAtual);
                System.out.print("Digite o NOVO preço: ");
                double novoPreco = scanner.nextDouble();
                scanner.nextLine(); 

                service.ProdutoService prodService = new service.ProdutoService();
                if (prodService.validarPreco(novoPreco)) {
                    service.atualizarPrecoNoBanco(escolhaUsuario, nomeProduto, novoPreco);
                }
            } else {
                System.out.println("Aviso: Nenhum produto com esse nome foi encontrado neste mercado.");
            }
        } else {
            System.out.println("Opção de mercado inválida!");
        }
    }

    private void deletarProduto() {
        System.out.println("\nSelecione o mercado de onde deseja excluir o produto:");
        for (int i = 0; i < rede.size(); i++) {
            System.out.println((i + 1) + " - " + rede.get(i).getNomeFantasia());
        }
        
        System.out.print("Escolha o mercado: ");
        int escolhaUsuario = scanner.nextInt();
        scanner.nextLine(); 

        if (escolhaUsuario >= 1 && escolhaUsuario <= rede.size()) {
            System.out.print("Digite o nome exato do produto que deseja DELETAR: ");
            String nomeProduto = scanner.nextLine();
            
            System.out.print("Tem certeza que deseja excluir o produto '" + nomeProduto + "'? (S/N): ");
            String confirmacao = scanner.nextLine().trim().toUpperCase();
            
            if (confirmacao.equals("S")) {
                service.deletarProdutoNoBanco(escolhaUsuario, nomeProduto);
            } else {
                System.out.println("Operação cancelada pelo usuário.");
            }
        } else {
            System.out.println("Opção de mercado inválida!");
        }
    }

    private void cadastrarEstabelecimento() {
        System.out.println("\n=== CADASTRAR NOVO ESTABELECIMENTO ===");
        System.out.print("Nome do Estabelecimento: ");
        String nome = scanner.nextLine();
        
        System.out.print("Endereço / Bairro: ");
        String bairro = scanner.nextLine();
        
        System.out.println("Tipo de Estabelecimento:");
        System.out.println("1 - Popular");
        System.out.println("2 - Central");
        System.out.print("Escolha: ");
        int tipo = scanner.nextInt();
        scanner.nextLine(); 

        if (tipo == 1 || tipo == 2) {
            if (nome.trim().isEmpty() || bairro.trim().isEmpty()) {
                System.out.println("Erro: Nome e Endereço não podem ser vazios.");
            } else {
                service.salvarEstabelecimentoNoBanco(nome, bairro, tipo);
                if (tipo == 1) {
                    rede.add(new EstabelecimentoPopular(nome, bairro));
                } else {
                    rede.add(new EstabelecimentoCentral(nome, bairro));
                }
            }
        } else {
            System.out.println("Tipo de estabelecimento inválido!");
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