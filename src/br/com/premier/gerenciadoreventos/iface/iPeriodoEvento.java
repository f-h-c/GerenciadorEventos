package br.com.premier.gerenciadoreventos.iface;

import br.com.premier.gerenciadoreventos.models.Palestra;

public interface iPeriodoEvento {

  public boolean tempoOk();
  
  public boolean addPalestra(Palestra palestra);
  
  public int getTempoTotal();

  public void mostrar();

  public void alocarPalestras();

}