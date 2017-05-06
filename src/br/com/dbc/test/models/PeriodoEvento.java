package br.com.dbc.test.models;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.dbc.test.iface.iPeriodoEvento;
import br.com.dbc.test.infra.PropertiesConsumer;

public abstract class PeriodoEvento implements iPeriodoEvento {

  private int tempoMaximo;
  private int tempoMinimo;
  private Calendar horaIni;
  private List<Palestra> palestras = new ArrayList<>();
  private SimpleDateFormat dataFormatada;

  public PeriodoEvento(int min, int max, Calendar horaIni) {
    String formatoHora = "hh:mma";
    this.tempoMinimo = min;
    this.tempoMaximo = max;
    this.horaIni = horaIni;
    
    try {
      formatoHora = PropertiesConsumer.getInstance("./properties/parametros.properties").getProp("formato.hora", formatoHora);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    
    dataFormatada = new SimpleDateFormat(formatoHora);
  }
  
  protected abstract String getEventoFimPeriodo();

  @Override
  public boolean addPalestra(Palestra palestra) {
    if (!palestra.agendada() && parcialmenteOk(palestra.getMinutes())) {
      palestras.add(palestra);
      
      return true;
    }
    
    return false;
  }
  
  @Override
  public void alocarPalestras() {
    for (Palestra p : palestras)
      p.agendada(true);
  }
  
  @Override
  public void mostrar() {
    Calendar horario = (Calendar) horaIni.clone();
    
    for (Palestra p : palestras) {
      System.out.println(dataFormatada.format(horario.getTime()) + " " + p);
      horario.add(Calendar.MINUTE, p.getMinutes());
    }
    
    System.out.println(dataFormatada.format(horario.getTime()) + " " + getEventoFimPeriodo());
  }

  @Override
  public int getTempoTotal() {
    int result = 0;

    for (Palestra p : palestras)
      result += p.getMinutes();

    return result;
  }

  public boolean parcialmenteOk(int tempo) {
    return tempo + getTempoTotal() <= tempoMaximo;
  }

  public int getTempoMaximo() {
    return tempoMaximo;
  }

  public void setTempoMaximo(int max) {
    this.tempoMaximo = max;
  }

  public int getTempoMinimo() {
    return tempoMinimo;
  }

  public void setTempoMinimo(int min) {
    this.tempoMinimo = min;
  }

  public Calendar getHoraIni() {
    return horaIni;
  }

  public void setHoraIni(Calendar horaIni) {
    this.horaIni = horaIni;
  }

}