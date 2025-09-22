 

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
        
        Connection conexao = b.conectar("comercio");
       
       Produto p = new Produto("Café Gelado",9.90);
               b.salvar(p, conexao);
      
}
}
