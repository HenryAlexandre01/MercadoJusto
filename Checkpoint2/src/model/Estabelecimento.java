package model;
import java.util.ArrayList;

public class Estabelecimento {
    private String nomeFantasia;
    private String endereco;
    private ArrayList<Produto> listaProdutos;

    public Estabelecimento(String nome, String endereco) {
        this.nomeFantasia = nome;
        this.endereco = endereco;
        this.listaProdutos = new ArrayList<>();
    }

    public String getNomeFantasia() { return nomeFantasia; }
    public String getEndereco() { return endereco; }
    public ArrayList<Produto> getListaProdutos() { return listaProdutos; }
}