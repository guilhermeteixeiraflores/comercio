 

package guilhermeteixeira.comercio;

import guilhermeteixeira.comercio.controle.Banco;
import guilhermeteixeira.comercio.modelo.Carrinho;
import guilhermeteixeira.comercio.modelo.Produto;
import guilhermeteixeira.comercio.view.GUIMenu;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author GUILHERME TEIXEIRA FLORES CPF:126.202.918.01
 */
public class Comercio {
/*
    public static void main(String[] args) {
       Banco b = new Banco();
        Connection conexao = b.conectar("comercio");
       
       Produto p = new Produto("Café Gelado",9.90);
               b.salvar(p, conexao);
      }
}*/
          public static void main (String args[]){
              Carrinho carrinho = new Carrinho();
              GUIMenu janelaPrincipal = new GUIMenu();
               Banco b = new Banco();
                b.inicializarBanco();
                
                ArrayList<Produto> produtos = b.buscarPorTrechoNome("");
                
                for(Produto produto: produtos){
                    produto.apresentarProduto();
                }
                
                b = null;
                
              java.awt.EventQueue.invokeLater(new Runnable(){
              public void run(){
                  janelaPrincipal.setVisible(true);
                  janelaPrincipal.getJInternalFrameCadastroProduto().setVisible(false);
                  janelaPrincipal.getJInternalFramePesquisar().setVisible(false);
                  janelaPrincipal.getJInternalFrameEditarCadastro().setVisible(false);
                  janelaPrincipal.getJInternalFrameCarrinho().setVisible(false);
              }
              } );  
                      }
}
                      
                   
         
         