package br.com.dbc.test.iface;

import br.com.dbc.test.models.Palestra;

public interface iPeriodoEvento {

  public boolean tempoOk();
  
  public boolean addPalestra(Palestra palestra);
  
  public int getTempoTotal();

  public void mostrar();

  public void alocarPalestras();

}