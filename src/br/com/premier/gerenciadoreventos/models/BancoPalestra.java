package br.com.premier.gerenciadoreventos.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.premier.gerenciadoreventos.converters.PalestraConverter;
import br.com.premier.gerenciadoreventos.exceptions.ConvertionException;
import br.com.premier.gerenciadoreventos.iface.iBancoPalestra;

public class BancoPalestra implements iBancoPalestra {
  private List<Palestra> banco = new ArrayList<Palestra>();

  @Override
  public void addPalestra(Palestra palestra) {
    banco.add(palestra);
  }

  @Override
  public void mostrar() {
    for (Palestra p : banco)
      System.out.println(p);
  }

  private void ordenaBanco() {
    Collections.sort(banco);
  }

  @Override
  public void consomeArquivoPalestra(String fileName) {
    try {
      FileReader arq = new FileReader(fileName);
      BufferedReader fileReader = new BufferedReader(arq);

      String linha = fileReader.readLine();

      while (linha != null) {
        if (linha != null && linha.trim().length() > 0)
          try {
            addPalestra(PalestraConverter.convert(linha));
          }
          catch (ConvertionException e) {
            e.printStackTrace();
          }

        linha = fileReader.readLine();
      }

      ordenaBanco();

      arq.close();
    }
    catch (IOException e) {
      System.err.printf("Erro ao abrir o arquivo: %s.\n", e.getMessage());
    }
  }

  @Override
  public int getTempoTotal() {
    int result = 0;

    for (Palestra p : banco)
      result += p.getMinutes();

    return result;
  }

  @Override
  public int getTotalPalestras() {
    return banco.size();
  }

  @Override
  public int getTotalPalestrasNaoAgendadas() {
    int result = 0;

    for (Palestra p : banco)
      result += p.agendada() ? 0 : 1;

    return result;
  }

  @Override
  public boolean vazio() {
    return banco.isEmpty();
  }

  @Override
  public Palestra getPalestra(int posicao) {
    return banco.get(posicao);
  }

  public static void main(String[] args) {

    /*
     * Talk t = new Talk("Palestra 1", 20); bank.addTalk(t);
     * 
     * t = new Talk("Palestra 2", 40); bank.addTalk(t);
     * 
     * t = new Talk("Palestra 3", 30); bank.addTalk(t);
     * 
     * t = new Talk("Palestra 4", 10); bank.addTalk(t);
     */

    // bank.show();
  }
}
