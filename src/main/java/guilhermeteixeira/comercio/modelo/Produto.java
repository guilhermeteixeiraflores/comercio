/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guilhermeteixeira.comercio.modelo;

import guilhermeteixeira.comercio.controle.Banco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author GUILHERME
 */
public class Produto {

    private double valorCompra;
   private String fornecedor;
   private String telefoneForne;    
    private int id;
    private String nome;
    private double preco;
    
    
     public Produto(String nome1, double valorCompra1, String fornecedor1, String telefone, double preco1){
        this.id = 0;
        this.nome = nome1;
        this.valorCompra = valorCompra1;
        this.fornecedor = fornecedor1;
        this.telefoneForne = telefone;
        this.preco = preco1;
    }
    
     public Produto(double valorCompra, String fornecedor, String telefoneForne, String nome, double preco){
         this.id = 0;
         this.valorCompra = valorCompra;
         this.fornecedor = fornecedor;
         this.telefoneForne = telefoneForne;
         this.nome = nome;
         this.preco = preco;
        //   salvar(nome, preco);
        }
       public Produto() {
        this.id = 0;
        this.nome = "";
        this.preco = 0.0;
        this.valorCompra = 0.0;
        this.fornecedor = "";
        this.telefoneForne = "";
    }
    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the preco
     */
    public double getPreco() {
        return preco;
    }
    public double getValorCompra() {
    return valorCompra;
}

public void setValorCompra(double valorCompra) {
    this.valorCompra = valorCompra;
}

public String getFornecedor() {
    return fornecedor;
}

public void setFornecedor(String fornecedor) {
    this.fornecedor = fornecedor;
}

public String getTelefoneForne() {
    return telefoneForne;
}

public void setTelefoneForne(String telefoneForne) {
    this.telefoneForne = telefoneForne;
}

    /**
     * @param preco the preco to set
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }
    public void apresentarProduto(){
        System.out.println("Nome: "+nome+",R$ "+valorCompra+" ,Fornecedor"+fornecedor+" ,TelefoneForne"+telefoneForne+",R$ "+preco+",ID:"+id);
    }
    
    
         public void salvar(String nome, double valorCompra, String fornecedor, String telefoneForne, Double preco){
                Banco b = new Banco();
                Connection conexao = b.conectar();
                 b.salvar(nome, valorCompra, fornecedor, telefoneForne, preco, conexao);
}
        
            public ArrayList<Produto> pesquisar(String nome){
                 Banco b = new Banco();
                Connection conexao = b.conectar();
                ArrayList<Produto> produtos = b.buscarPorTrechoNome(nome);
                
                return produtos;
            }
            public void deletar(int id) throws SQLException{
                  Banco b = new Banco();
                Connection conexao = b.conectar();
                b.deletar(id);
            }
          public void editar(String nome, double valorCompra, String fornecedor, String telefoneForne, double preco, int id){
            Banco b = new Banco();
           Connection conexao = b.conectar();
            b.editar(nome, valorCompra, fornecedor, telefoneForne, preco, id);
}
                public Produto(String nome, double valorCompra, String fornecedor, String telefoneForne, double preco, int id) {
                 this.nome = nome;
                 this.valorCompra = valorCompra;
                 this.fornecedor = fornecedor;
                 this.telefoneForne = telefoneForne;
                this.preco = preco;
}
           
    /**      
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

  public Produto buscarPorId(int idPesquisar) {
    Banco b = new Banco();
    Connection conexao = b.conectar();
        Produto produto = b.buscarPorId(idPesquisar);
    return produto;
}   
     public void adicionarCarrinho(int id, int quantidade){
        Produto produto = buscarPorId(id);
        Banco b = new Banco();
        Connection conexao = b.conectar();
        b.adicionarCarrinho(produto, quantidade, conexao);
        
     }
     public HashMap<Produto, Integer> recuperarCarrinho(){
         Banco b = new Banco();
        Connection conexao = b.conectar();
        HashMap<Produto, Integer> carrinho = b.buscarCarrinho(conexao);
          return carrinho;
       }     
     public void mostrarCarrinho(HashMap<Produto, Integer> carrinho) {
    if (carrinho.isEmpty()) {
        System.out.println("O carrinho está vazio.");
        return;
    }

    System.out.println("===== Carrinho de Compras =====");
    for (Map.Entry<Produto, Integer> entry : carrinho.entrySet()) {
        Produto produto = entry.getKey();
        int quantidade = entry.getValue();

        System.out.println("Produto: " + produto.getNome());
        System.out.println("Preço de Venda: R$ " + produto.getPreco());
        System.out.println("Quantidade: " + quantidade);
        System.out.println("-------------------------------");
    }
}
}   
