package br.com.premier.gerenciadoreventos.iface;

import br.com.premier.gerenciadoreventos.models.PeriodoDia;

public interface iTrilhaEvento {

  iPeriodoEvento getInstance(PeriodoDia p);

  void setPeriodo(PeriodoDia p, iPeriodoEvento periodo);

  iPeriodoEvento getPeriodo(PeriodoDia p);

  void mostrar();

}