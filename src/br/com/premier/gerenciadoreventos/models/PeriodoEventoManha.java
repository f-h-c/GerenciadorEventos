package br.com.premier.gerenciadoreventos.models;

import java.util.Calendar;

/**
 * Período da manhã da trilha de um evento
 * 
 * @author fhc
 *
 */
public class PeriodoEventoManha extends PeriodoEvento {
  
  /**
   * 
   * @param tempo Tamanho, em minutos, do período da manhã da trilha de um evento
   * @param horaIni Hora de início do período da manhã da trilha de um evento
   */
  public PeriodoEventoManha(int tempo, Calendar horaIni) {
    super(tempo, tempo, horaIni);
  }
  
  @Override
  public boolean tempoOk() {
    int minutos = getTempoTotal();
    
    return (minutos == getTempoMinimo());
  }

  @Override
  protected String getEventoFimPeriodo() {
    return "Lunch";
  }

}
