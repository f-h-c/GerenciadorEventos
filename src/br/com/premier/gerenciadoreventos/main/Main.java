package br.com.premier.gerenciadoreventos.main;

import br.com.premier.gerenciadoreventos.iface.iEvento;
import br.com.premier.gerenciadoreventos.models.Evento;

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
