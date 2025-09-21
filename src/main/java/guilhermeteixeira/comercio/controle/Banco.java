
package guilhermeteixeira.comercio.controle;

import guilhermeteixeira.comercio.modelo.Produto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Banco {
    private String url ;
    private String usuario;
    private String senha ; 
    
          public  Banco(){
          url = "jdbc:mysql://localhost:3306/comercio";
          usuario = "root";
          senha = "root";
      }
        public Connection conectar() {
        try {
          Connection conexao =  DriverManager.getConnection(url, usuario, senha);
          
           System.out.println("Conexao Com o Banco de Dados Com Sucesso!");
             return conexao;
             
        } catch (SQLException e){
            System.out.println("Não foi Possivel conectar no Banco de Dados");
            return null;
            
        }
    }
    public void salvar (Produto produto, Connection conexao){
        String sql = "INSERT INTO produto(nome, preco) VALUES(?, ?)";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
             stmt.setString(1, produto.getNome());
             stmt.setDouble(2, produto.getPreco());
             
             int linhasAfetadas = stmt.executeUpdate();
             
             if (linhasAfetadas > 0){
                 System.out.println("O Produto Foi Salvo com Sucesso!");
             }
             
        } catch(SQLException e) {
            System.out.println("O Produto Não Foi Salvo !");
        }
    }
}
