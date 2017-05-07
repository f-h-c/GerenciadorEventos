package br.com.premier.gerenciadoreventos.iface;

import br.com.premier.gerenciadoreventos.models.PeriodoTrilha;

/**
 * Interface da trilha de um evento
 * 
 * @author fhc
 *
 */
public interface iTrilhaEvento {

  /**
   * Disponibiliza uma instância do perídio solicitado.
   */
  public iPeriodoEvento getInstance(PeriodoTrilha p);

  /**
   * Define o período informado na trilha do evento.
   */
  public void setPeriodo(PeriodoTrilha p, iPeriodoEvento periodo);

  /**
   * Disponibiliza o período informado da trilha do evento.
   */
  public iPeriodoEvento getPeriodo(PeriodoTrilha p);

  /**
   * Exibe todas as palestras da trilha
   */
  public void mostrar();

}