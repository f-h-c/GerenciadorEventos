package br.com.dbc.test.models;

import java.util.Calendar;

import br.com.dbc.test.iface.iPeriodoEvento;
import br.com.dbc.test.iface.iTrilhaEvento;

public class TrilhaEvento implements iTrilhaEvento {
  private iPeriodoEvento manha = null;
  private iPeriodoEvento tarde = null;
  private int tempoManha;
  private Calendar horaIniManha;
  private int tempoTardeMin;
  private int tempoTardeMax;
  private Calendar horaIniTarde;

  public TrilhaEvento(int tempoManha, Calendar horaIniManha, int tempoTardeMin, int tempoTardeMax, Calendar horaIniTarde) {
    this.tempoManha = tempoManha;
    this.horaIniManha = horaIniManha;
    this.tempoTardeMin = tempoTardeMin;
    this.tempoTardeMax = tempoTardeMax;
    this.horaIniTarde = horaIniTarde;
  }

  @Override
  public iPeriodoEvento getInstance(PeriodoDia p) {
    iPeriodoEvento result = null;

    if (p == PeriodoDia.MANHA)
      result = new PeriodoEventoManha(tempoManha, horaIniManha);
    else
      if (p == PeriodoDia.TARDE)
        result = new PeriodoEventoTarde(tempoTardeMin, tempoTardeMax, horaIniTarde);

    return result;
  }

  @Override
  public void setPeriodo(PeriodoDia p, iPeriodoEvento periodo) {
    if (periodo != null) {
      if (p == PeriodoDia.MANHA)
        manha = periodo;
      else
        if (p == PeriodoDia.TARDE)
          tarde = periodo;

      periodo.alocarPalestras();
    }
  }

  @Override
  public iPeriodoEvento getPeriodo(PeriodoDia p) {
    if (p == PeriodoDia.MANHA)
      return manha;
    else
      if (p == PeriodoDia.TARDE)
        return tarde;

    return null;
  }

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
