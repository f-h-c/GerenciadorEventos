package br.com.premier.gerenciadoreventos.main;

import br.com.premier.gerenciadoreventos.iface.iEvento;
import br.com.premier.gerenciadoreventos.models.Evento;

/**
 * Classe principal do sistema.
 * 
 * Ao executar esta classe, deve-se passar o caminho para o arquivo texto com as palestras do evento.
 * 
 * Se for possível criar o evento a partir das palestras contidas no arquivo passado, as trilhas serão exibidas. Caso contrário, será informada uma mensagem de erro.
 * 
 * @author fhc
 */ 
public class Main {

  public static void main(String[] args) {
    if (args.length == 1) {
      iEvento e = new Evento(args[0]); 

      if (e.montaEvento())
        e.mostrar();
      else
        System.out.println("Não foi possível alocar todas as palestras da forma pré-estabelecida.");
    }
    else
      System.out.println("Informe o caminho para o arquivo texto com as palestras do evento.");
  }
}
