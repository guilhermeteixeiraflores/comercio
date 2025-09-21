 

package guilhermeteixeira.comercio;

import guilhermeteixeira.comercio.controle.Banco;
import guilhermeteixeira.comercio.modelo.Produto;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author GUILHERME TEIXEIRA FLORES CPF:126.202.918.01
 */
public class Comercio {

    public static void main(String[] args) {
       Banco b = new Banco();
       Connection conexao = b.conectar();
       
       Produto p = new Produto("Café Expresso", 5.90);
       
       if (conexao != null){
           try{
               b.salvar(p, conexao);
                 conexao.close();
       }catch (SQLException e){
           System.out.println("Erro ao Fechar a conexão com o Banco de Dados!");
       }
    }
}
}
