
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
import java.util.HashMap;
import java.util.Map;

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
   public void salvar(String nome, double valorCompra, String fornecedor, String telefoneForne, double preco, Connection conexao) {
    String sql = "INSERT INTO produto(nome, valorCompra, fornecedor, telefoneForne, preco) VALUES (?, ?, ?, ?, ?)";
    try {
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, nome);
        stmt.setDouble(2, valorCompra);
        stmt.setString(3, fornecedor);
        stmt.setString(4, telefoneForne);
        stmt.setDouble(5, preco);

        int linhasAfetadas = stmt.executeUpdate();
        if (linhasAfetadas > 0) {
            System.out.println("Produto salvo com sucesso!");
        }

        stmt.close();
        conexao.close();
    } catch (SQLException e) {
        System.out.println("Erro ao salvar produto: " + e.getMessage());
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
       public void salvar(Produto produto, Connection conexao) {
    salvar(
        produto.getNome(),
        produto.getValorCompra(),
        produto.getFornecedor(),
        produto.getTelefoneForne(),
        produto.getPreco(),
        conexao );
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
                    double valorCompra = rs.getDouble("valorCompra");
                    String fornecedor = rs.getString("fornecedor");
                    String telefoneForne= rs.getString("telefoneForne");
                    String nome = rs.getString("nome");
                    double preco = rs.getDouble("preco");
                    Produto produto = new Produto(nome, valorCompra, fornecedor, telefoneForne, preco);
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
               
             
            
         public void editar(String nome, double valorCompra, String fornecedor, String telefoneForne, double preco, int id) {
    
         String sql = "UPDATE produto SET nome = ?, valorCompra = ?, fornecedor = ?, telefoneForne = ?, preco = ? WHERE id = ?";
        try {
            Connection conexao = conectar();
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, nome);
        stmt.setDouble(2, valorCompra);
        stmt.setString(3, fornecedor);
        stmt.setString(4, telefoneForne);
        stmt.setDouble(5, preco);
        stmt.setInt(6, id);
        int linhasAfetadas = stmt.executeUpdate();
        System.out.println("Linhas atualizadas: " + linhasAfetadas); // Debug útil
        stmt.executeUpdate();
        stmt.close();
        conexao.close();
    } catch (SQLException e) {
        e.printStackTrace();
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
            double valorCompra = rs.getDouble("valorCompra");
            String fornecedor = rs.getString("fornecedor");
            String telefoneForne = rs.getString("telefoneForne");
            double preco = rs.getDouble("preco");

            produtoEncontrado = new Produto(nome, valorCompra, fornecedor, telefoneForne, preco);
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
             public HashMap<Produto, Integer> buscarCarrinho(Connection conexao) {
    String sql = "SELECT * FROM carrinho";
    HashMap<Produto, Integer> carrinho = new HashMap<>();

    try {
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            double preco = rs.getDouble("preco");
            int quantidade = rs.getInt("quantidade");

           // Produto produto = new Produto(nome, preco );
          //  produto.setId(id);

          //  carrinho.put(produto, quantidade);
        }

        rs.close();
        stmt.close();
        conexao.close();

    } catch (SQLException e) {
        System.out.println("Erro ao buscar os produtos do carrinho no banco de dados.");
    }
        
    return carrinho;
}

    public void salvar(String nome, double preco, Connection conexao) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
                                      }
                 
                      
              


