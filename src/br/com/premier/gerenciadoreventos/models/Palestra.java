package br.com.premier.gerenciadoreventos.models;

public class Palestra implements Comparable<Palestra> {

  private String titulo;
  private int minutos;
  private boolean agendada = false;

  public Palestra(String title, int minutes) {
    this.titulo = title;
    this.minutos = minutes;
  }

  public String getTitle() {
    return titulo;
  }

  public int getMinutes() {
    return minutos;
  }

  public boolean agendada() {
    return agendada;
  }

  public void agendada(boolean agendada) {
    this.agendada = agendada;
  }

  @Override
  public int compareTo(Palestra o) {
    if (this.minutos > o.getMinutes())
      return -1;
    else
      if (this.minutos < o.getMinutes())
        return 1;

    return 0;
  }
  
  @Override
  public String toString() {
    return titulo + " - " + minutos + " min"; 
  }

}
