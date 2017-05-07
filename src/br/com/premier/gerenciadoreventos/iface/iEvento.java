package br.com.premier.gerenciadoreventos.iface;

/**
 * Interface responsável pelo gerenciamento de um evento
 * 
 * @author fhc
 *
 */
public interface iEvento {

  /**
   * Monta o evento a partir das definições pré-estabelecidas e das palestras carregadas
   * @return
   */
  public boolean montaEvento();

  /**
   * Exibe todas as informações do evento montado.
   */
  public void mostrar();

}