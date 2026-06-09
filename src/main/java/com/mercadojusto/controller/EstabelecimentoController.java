package com.mercadojusto.controller;

import com.mercadojusto.model.Estabelecimento;
import com.mercadojusto.repository.EstabelecimentoRepository;
import com.mercadojusto.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EstabelecimentoController {

@Autowired
private EstabelecimentoRepository estRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

 //abre o formulário de cadastro de loja
    @GetMapping("/estabelecimento/novo")
    public String abrirCadastroEstabelecimento(Model model) {
        model.addAttribute("estabelecimento", new Estabelecimento());
        return "cadastro-estabelecimento"; 
    }


    //busca todas as lojas cadastradas no banco e envia para a tela de listagem
    @GetMapping("/estabelecimento/lista")
    public String listarEstabelecimentos(Model model) {
        model.addAttribute("estabelecimentos", estRepository.findAll());
        return "lista-estabelecimentos"; 
    }

    // Salva o estabelecimento vindo do formulário
    @PostMapping("/estabelecimento/salvar")
    public String salvarEstabelecimento(@Valid Estabelecimento estabelecimento, BindingResult result) {
        //se tiver campos obrigatórios vazios, volta para o formulário
        if (result.hasErrors()) {
            System.out.println("Erros de validação: " + result.getAllErrors());
            return "cadastro-estabelecimento";
        }

        try {
            estRepository.save(estabelecimento);
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        
       //retorna para a tela inicial
        return "redirect:/";
    }
}