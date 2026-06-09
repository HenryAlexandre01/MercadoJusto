package com.mercadojusto.controller;

import com.mercadojusto.repository.ProdutoRepository;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.mercadojusto.repository.EstabelecimentoRepository;
import com.mercadojusto.model.Produto;
import java.util.List; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    //buscar dados de produtos, lojas e categorias
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstabelecimentoRepository estRepository;


    //carrega todos os dados necessários para a tela inicial
    @GetMapping("/")
    public String index(Model model) {
    List<Produto> todosProdutos = produtoRepository.findAll();
    
    // CHAMADA DO CÁLCULO AQUI:
    calcularVariacoes(todosProdutos);
    model.addAttribute("produtos", todosProdutos);
    model.addAttribute("estabelecimentos", estRepository.findAll());    
    return "index";
}

    
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