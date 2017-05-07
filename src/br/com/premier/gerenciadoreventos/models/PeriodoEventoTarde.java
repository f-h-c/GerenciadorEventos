package br.com.premier.gerenciadoreventos.models;

import java.util.Calendar;

/**
 * Período da tarde da trilha de um evento
 * 
 * @author fhc
 *
 */
public class PeriodoEventoTarde extends PeriodoEvento {
  
  /**
   * 
   * @param min Tamanho mínimo, em minutos, do período da tarde da trilha de um evento
   * @param max Tamanho máximo, em minutos, do período da tarde da trilha de um evento
   * @param horaIni Hora de início do período da tarde da trilha de um evento
   */
  public PeriodoEventoTarde(int min, int max, Calendar horaIni) {
    super(min, max, horaIni);
  }
  
  @Override
  public boolean tempoOk() {
    int minutos = getTempoTotal();
    
    return (minutos >= getTempoMinimo()) && (minutos <= getTempoMaximo());
  }

  @Override
  protected String getEventoFimPeriodo() {
    return "Networking Event";
  }

}
