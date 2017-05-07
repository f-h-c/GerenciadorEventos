package br.com.premier.gerenciadoreventos.iface;

import br.com.premier.gerenciadoreventos.models.Palestra;

/**
 * Interface do período de um evento
 * 
 * @author fhc
 *
 */
public interface iPeriodoEvento {

  /**
   * Verifica se o tempo do período foi preenchido corretamente com as palestras.
   * 
   * @return Se o tempo do período foi preenchido corretamente
   */
  public boolean tempoOk();
  
  /**
   * Tenta adicionar uma palestra ao período da trilha do evento, verificando se a mesma se encaixa no período a partir das pré-definições.
   *  
   * @param palestra
   * @return Se a palestra foi adicionada ao período da trilha do evento
   */
  public boolean addPalestra(Palestra palestra);
  
  /**
   * Calcula e retorna o tempo total das palestras do período do evento.
   * 
   * @return Tempo total das palestras do período do evento
   */
  public int getTempoTotal();

  /**
   * Exibe todas as palestras do período do evento precedidas pelo seu horário de início.
   * 
   */
  public void mostrar();

  /**
   * Define como alocadas as palestras pertencentes ao período do evento.
   * 
   */
  public void alocarPalestras();

  /**
   * Verifica se o tempo da palestra informado estrapola ou não o tempo total do período de uma trilha do evento.
   * 
   * @param tempo Tempo total da palestra
   * @return Se o tempo total do período da trilha do evento continua válido com a palestra a ser adicionada
   */
  public boolean parcialmenteOk(int tempo);

}