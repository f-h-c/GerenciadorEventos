package br.com.premier.gerenciadoreventos.models;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.premier.gerenciadoreventos.iface.iPeriodoEvento;
import br.com.premier.gerenciadoreventos.infra.PropertiesHelper;

/**
 * Classe abstrata do período de uma trilha de um evento.
 * 
 * @author fhc
 *
 */
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
      //busca no arquivo de propriedades o formato que será exibido o horário da palestra
      formatoHora = PropertiesHelper.getInstance("./properties/parametros.properties").getProp("formato.hora", formatoHora);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    
    dataFormatada = new SimpleDateFormat(formatoHora);
  }
  
  /**
   * Informa o texto do acontecimento final do período da trilha de um evento.
   * 
   * @return
   */
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
  
  @Override
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