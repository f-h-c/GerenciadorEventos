package br.com.premier.gerenciadoreventos.iface;

import br.com.premier.gerenciadoreventos.models.Palestra;

public interface iBancoPalestra {

  public void addPalestra(Palestra talk);

  public void mostrar();

  public void consomeArquivoPalestra(String fileName);

  public boolean vazio();
  
  public int getTempoTotal();

  public int getTotalPalestras();

  public Palestra getPalestra(int posicao);

  public int getTotalPalestrasNaoAgendadas();

}