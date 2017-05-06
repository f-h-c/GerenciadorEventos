package br.com.dbc.test.iface;

import br.com.dbc.test.models.PeriodoDia;

public interface iTrilhaEvento {

  iPeriodoEvento getInstance(PeriodoDia p);

  void setPeriodo(PeriodoDia p, iPeriodoEvento periodo);

  iPeriodoEvento getPeriodo(PeriodoDia p);

  void mostrar();

}