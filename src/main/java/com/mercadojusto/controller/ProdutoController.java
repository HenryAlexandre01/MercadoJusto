package com.mercadojusto.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.mercadojusto.model.Produto;
import com.mercadojusto.repository.ProdutoRepository;
import com.mercadojusto.repository.EstabelecimentoRepository;

import java.util.List;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    //manipula dados de produtos, lojas e categorias
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;



    //exibe o formulário de cadastro, carregando as opções de loja e categoria
   @GetMapping("/novo")
    public String abrirCadastro(Model model) {
    model.addAttribute("produto", new Produto());
    model.addAttribute("estabelecimentos", estabelecimentoRepository.findAll());
    return "cadastro";
    }

    // Processa o salvamento do produto e atribui uma imagem padrão baseada no nome
    @PostMapping("/salvar")
    public String salvar(@Valid Produto produto, BindingResult result) {
        if (result.hasErrors()) {
            return "cadastro"; 
        }

        String nome = produto.getNome().toLowerCase();
        if (nome.contains("feijao")) {
            produto.setNomeImagem("feijao.png");
        } else if (nome.contains("arroz")) {
            produto.setNomeImagem("arroz.png");
        } else if (nome.contains("farinha")) {
            produto.setNomeImagem("farinha.png");
        } else if (nome.contains("ovo")) {
            produto.setNomeImagem("ovo.png");
        } else if (nome.contains("pasta de dente")) {
            produto.setNomeImagem("pasta-de-dente.png");
        } else if (nome.contains("picanha")) {
            produto.setNomeImagem("picanha.png");
        } else if (nome.contains("alcatra")) {
            produto.setNomeImagem("alcatra.png");    
        } else if (nome.contains("leite")) {
            produto.setNomeImagem("leite.png");
        } else if (nome.contains("pao pullman")) {
            produto.setNomeImagem("pao-pullman.png");
        } else if (nome.contains("heineken")) {
            produto.setNomeImagem("cerveja-heineken.png");
        } else if (nome.contains("corona")) {
            produto.setNomeImagem("cerveja-corona.png");
        } else if (nome.contains("agua de coco")) {
            produto.setNomeImagem("agua-coco.png");                                                                        
        } else if (nome.contains("macarrao")) {
            produto.setNomeImagem("macarrao.png");
        } else {
            produto.setNomeImagem("default.png");
        }
        
        produtoRepository.save(produto);
        return "redirect:/"; //retorna para a tela de inicio
    }

    //remove um produto do banco pelo ID
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Integer id) {
        produtoRepository.deleteById(id);
        return "redirect:/";
    }

    //busca um produto pelo ID para preencher o formulário de edição
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Integer id, Model model) {
    model.addAttribute("produto", produtoRepository.findById(id).orElse(null));
    model.addAttribute("estabelecimentos", estabelecimentoRepository.findAll());
    return "cadastro";
    }

    // Busca produtos por termo e calcula variações de preço comparado à média
    @GetMapping("/buscar")
    public String buscar(@RequestParam("nome") String nome, Model model) {
    List<Produto> produtosEncontrados = produtoRepository.findByNomeContainingIgnoreCaseOrderByPrecoAsc(nome);
    
    // Adicionamos a lista de estabelecimentos aqui para não sumir na tela
    model.addAttribute("estabelecimentos", estabelecimentoRepository.findAll());
    
    if (!produtosEncontrados.isEmpty()) {
        calcularVariacoes(produtosEncontrados);
        String nomeProduto = produtosEncontrados.get(0).getNome();
        
        // Passando o produto para a imagem do card
        model.addAttribute("produtoDestaque", produtosEncontrados.get(0));
        
        model.addAttribute("media", produtoRepository.calcularMediaPreco(nomeProduto));
        model.addAttribute("menorPreco", produtoRepository.encontrarMenorPreco(nomeProduto));
        model.addAttribute("maiorPreco", produtoRepository.encontrarMaiorPreco(nomeProduto));
        
        model.addAttribute("nomeLojaMenorPreco", produtoRepository.findNomeLojaMenorPreco(nomeProduto));
        model.addAttribute("nomeLojaMaiorPreco", produtoRepository.findNomeLojaMaiorPreco(nomeProduto));
    }
    
    model.addAttribute("produtos", produtosEncontrados);
    return "index"; 
}

    //auxiliar para comparar preço do produto com a média geral
    private void calcularVariacoes(List<Produto> produtos) {
        for (Produto p : produtos) {
            Double media = produtoRepository.calcularMediaPreco(p.getNome());
            if (media != null && media != 0) {
                double variacao = ((p.getPreco() - media) / media) * 100;
                p.setVariacao(variacao);
            } else {
                p.setVariacao(0.0);
            }
        }
    }
}