package br.com.premier.gerenciadoreventos.models;

import java.util.Calendar;

import br.com.premier.gerenciadoreventos.iface.iPeriodoEvento;
import br.com.premier.gerenciadoreventos.iface.iTrilhaEvento;

/**
 * Classe que implementa uma trilha do evento a ser criado.
 * 
 * @author fhc
 *
 */
public class TrilhaEvento implements iTrilhaEvento {
  private iPeriodoEvento manha = null;
  private iPeriodoEvento tarde = null;
  private int tempoManha;         //tempo total disponibilizado para o período da manhã
  private Calendar horaIniManha;  //horário de início do evento no perídio da manhã
  private int tempoTardeMin;      //tempo mínimo total disponibilizado para o período da tarde
  private int tempoTardeMax;      //tempo máximo total disponibilizado para o período da tarde
  private Calendar horaIniTarde;  //horário de início do evento no perídio da tarde

  public TrilhaEvento(int tempoManha, Calendar horaIniManha, int tempoTardeMin, int tempoTardeMax, Calendar horaIniTarde) {
    this.tempoManha = tempoManha;
    this.horaIniManha = horaIniManha;
    this.tempoTardeMin = tempoTardeMin;
    this.tempoTardeMax = tempoTardeMax;
    this.horaIniTarde = horaIniTarde;
  }

  @Override
  public iPeriodoEvento getInstance(PeriodoTrilha p) {
    iPeriodoEvento result = null;

    if (p == PeriodoTrilha.MANHA)
      result = new PeriodoEventoManha(tempoManha, horaIniManha);
    else
      if (p == PeriodoTrilha.TARDE)
        result = new PeriodoEventoTarde(tempoTardeMin, tempoTardeMax, horaIniTarde);

    return result;
  }

  @Override
  public void setPeriodo(PeriodoTrilha p, iPeriodoEvento periodo) {
    if (periodo != null) {
      if (p == PeriodoTrilha.MANHA)
        manha = periodo;
      else
        if (p == PeriodoTrilha.TARDE)
          tarde = periodo;

      //Caso o período seja atribuído, define todas as palestras contidas como alocadas, não permitindo que as mesmas sejam alocadas para outro período.
      periodo.alocarPalestras();
    }
  }
  
  @Override
  public iPeriodoEvento getPeriodo(PeriodoTrilha p) {
    if (p == PeriodoTrilha.MANHA)
      return manha;
    else
      if (p == PeriodoTrilha.TARDE)
        return tarde;

    return null;
  }

  /**
   * Exibe as palestras do período informado
   * @param periodo
   */
  private void mostrar(iPeriodoEvento periodo) {
    if (periodo != null)
      periodo.mostrar();
  }
  
  @Override
  public void mostrar() {
    mostrar(manha);
    mostrar(tarde);
  }
}
