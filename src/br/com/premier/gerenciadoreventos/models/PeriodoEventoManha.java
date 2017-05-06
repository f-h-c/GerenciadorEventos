package br.com.premier.gerenciadoreventos.models;

import java.util.Calendar;

public class PeriodoEventoManha extends PeriodoEvento {
  
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
