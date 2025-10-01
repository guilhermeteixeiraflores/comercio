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

/**
 *
 * @author GUILHERME
 */
public class Produto {

    public static Produto buscarPorId(int idPesquisar) {
    Banco b = new Banco();
    Connection conexao = b.conectar();
    Produto produto = b.buscarPorId(idPesquisar);
    return produto;
}
    private int id;
    private String nome;
    private double preco;
    
    public Produto(){
          this.id = 0;
         this.nome = "";
         this.preco = 0;
    }
    
     public Produto(String nome, double preco){
         this.id = 0;
         this.nome = nome;
         this.preco = preco;
         
      //   salvar(nome, preco);
         
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


    /**
     * @param preco the preco to set
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }
    public void apresentarProduto(){
        System.out.println("Nome: "+nome+",R$ "+preco+",ID:"+id);
    }
    
    
         public void salvar(String nome, double preco){
                Banco b = new Banco();
                Connection conexao = b.conectar();
                b.salvar(nome, preco, conexao);
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
      public void editar(String nome, double preco, int id){
            Banco b = new Banco();
             Connection conexao = b.conectar();
             b.editar(nome, preco, id); // Usa os dados NOVOS recebidos como parâmetro
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

  public Produto buscarPorid(int idPesquisar) {
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
     public ArrayList<Produto> recuperarCarrinho(){
         Banco b = new Banco();
        Connection conexao = b.conectar();
        ArrayList<Produto> produtos = b.buscarCarrinho();
        return produtos;
        
     }
}

       


     
