package br.com.dbc.test.models;

import java.util.Calendar;

public class PeriodoEventoTarde extends PeriodoEvento {
  
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
