
package guilhermeteixeira.comercio.controle;

import guilhermeteixeira.comercio.modelo.Produto;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Normalizer;
import java.util.ArrayList;

public class Banco {
    private String url ;
    private String usuario;
    private String senha ; 
    
          public  Banco(){
          url = "jdbc:mysql://localhost:3306";
          usuario = "root";
          senha = "root";
          
           }
        public Connection conectar() {
        try {
            url = "jdbc:mysql://localhost:3306/comercio";
             usuario = "root";
             senha = "root";
          Connection conexao =  DriverManager.getConnection(url, usuario, senha);
          
           System.out.println("Conexao Com o Banco de Dados Com Sucesso!");
             return conexao;
             
        } catch (SQLException e){
            System.out.println("Não foi Possivel conectar no Banco de Dados");
            return null;
            
        }
    }
    public void salvar(String nome, double preco, Connection conexao){
        String sql = "INSERT INTO produto(nome, preco) VALUES(?, ?)";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            
             stmt.setString(1, nome);
             stmt.setDouble(2, preco);
             
             int linhasAfetadas = stmt.executeUpdate();
             
             if (linhasAfetadas > 0){
                 System.out.println("O Produto Foi Salvo com Sucesso!");
             }
             
             stmt.close();
             conexao.close();
        } catch(SQLException e) {
            System.out.println("O Produto Não Foi Salvo !");
        }
    }
       public void inicializarBanco() {
           url = "jdbc:mysql://localhost:3306";
          usuario = "root";
          senha = "root";
          
           try {
               Connection conexao = DriverManager.getConnection(url, usuario, senha);
               
               Statement stmt = conexao.createStatement();
               try {
               InputStream is = new FileInputStream("banco.sql");
               InputStreamReader isr = new InputStreamReader (is);
               BufferedReader br = new BufferedReader(isr);
               
               String linha;
               StringBuilder sql = new StringBuilder();
               
               linha = br.readLine();
               
               while (linha != null){
                   System.out.println(linha);
                   sql.append(linha).append("\n");
                   
               if (linha.trim().endsWith(";")) {
                   stmt.execute(sql.toString());
                   sql.setLength(0);
                   
               }  
                linha = br.readLine();
               
               }
               stmt.close();
               conexao.close();
               } catch (Exception e){
                 System.out.println("Não foi possivel ler o Arquivo banco.sql");
               }
           
           } catch (SQLException e){
             System.out.println("Erro ao conectar no banco de dados no metodo inicializr banco");
          }
       }
       public ArrayList<Produto> buscarPorTrechoNome(String trechoNome){
           ArrayList<Produto> listaDeProdutos = new ArrayList<>();
           String trechoNormalizado = "%" + normalizarTexto(trechoNome) + "%";
           String sql = "SELECT * FROM produto WHERE nome LIKE ?";
            try {
                Connection conexao = conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql);
                
                stmt.setString (1, trechoNormalizado);
                
                ResultSet rs = stmt.executeQuery();
                
                while (rs.next()){
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    double preco = rs.getDouble("preco");
                    Produto produto = new Produto(nome, preco);
                    produto.setId(id);
                    listaDeProdutos.add(produto);
                    
                }
                   rs.close();
                   stmt.close();
                   conexao.close();
                  } catch (SQLException e){
                      System.out.println("Não conseguiu conectar no banco de dados no metodo buscarPorTrechoNome()");
                  }
            return listaDeProdutos;
                      }
       private String normalizarTexto(String trecho){
          return Normalizer.normalize(trecho, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
          
       }
       public void deletar(int id) throws SQLException{
           try{ 
           String sql = "DELETE FROM produto WHERE id =?";
           Connection conexao = conectar();
           PreparedStatement stmt = conexao.prepareStatement(sql);
           stmt.setInt(1, id);
           
           int linhasAfetadas = stmt.executeUpdate();
           if (linhasAfetadas > 0){
               System.out.println("O Produto de ID:" +id+ "Foi Excluido com sucesso!");
                } else {
                System.out.println("O Produto de ID:" +id+ "Não Foi Encontrado!");
           }
           stmt.close();
           conexao.close();
        } catch (SQLException e){
              System.out.println("Houve um erro ao tentar conectar no banco de dados no metodo deletar!");
}               
                 
}
               
             
            
          public void editar(String nome, double preco, int id){
             String sql = "UPDATE produto SET nome = ?, preco = ? WHERE id = ?";
             
             try {
                 Connection conexao = conectar();
                 PreparedStatement stmt = conexao.prepareCall(sql);
                 
              stmt.setString(1, nome);
              stmt.setDouble(2, preco);
              stmt.setInt(3, id);
              
              int linhasAfetadas = stmt.executeUpdate();
              
              if (linhasAfetadas > 0) {
                  System.out.println("O Cadastro foi editado com Sucesso!");
              } else {
                  System.out.println("Não houve alteração na edição do cadastro!");
              }
              stmt.close();
              conexao.close();
           }catch(SQLException e) {
             System.out.println("Não foi possivel conectar no banco de dados para editar");
         }

    
    }
      
             public Produto buscarPorId(int id){
             String sql = "SELECT * FROM produto WHERE id = ?";
             Produto produtoEncontrado = null;
             try {
                 Connection conexao = conectar();
                 PreparedStatement stmt = conexao.prepareStatement(sql);
                 stmt.setInt(1, id);
                 
                 ResultSet rs = stmt.executeQuery();
                 
                 if (rs.next()){
                     String nome = rs.getString("nome");
                     double preco = rs.getDouble("preco");
                     produtoEncontrado = new Produto (nome, preco);
                     produtoEncontrado.setId(id);
                     
                 }
                 rs.close();
                 stmt.close();
                 conexao.close();
                 
                
             } catch (SQLException e) {
                 System.out.println("Não foi possivel conectar no banco de dados no metodo buscarPorId");
                 
                 
             }
              return produtoEncontrado;
               }
              public void adicionarCarrinho(Produto produto, int quantidade, Connection conexao){
        String sql = "INSERT INTO carrinho(nome, preco, quantidade) VALUES(?, ?, ?)";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            
             stmt.setString(1, produto.getNome());
             stmt.setDouble(2, produto.getPreco());
             stmt.setInt(3, quantidade);
             
             int linhasAfetadas = stmt.executeUpdate();
             
             if (linhasAfetadas > 0){
                 System.out.println("O Produto Foi Salvo No Carrinho com Sucesso!");
             }
             
             stmt.close();
             conexao.close();
        } catch(SQLException e) {
            System.out.println("O Produto Não Foi Salvo no Carrinho do banco de dados !");
        }
         }
}

