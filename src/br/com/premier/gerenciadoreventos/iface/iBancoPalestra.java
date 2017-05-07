package br.com.premier.gerenciadoreventos.iface;

import br.com.premier.gerenciadoreventos.models.Palestra;

/**
 * Interface do armazenador das palestras que comporão o evento.
 * 
 * @author fhc
 *
 */
public interface iBancoPalestra {

  /**
   * Adiciona a palestra ao banco de palestras.
   * 
   * @param palestra
   */
  public void addPalestra(Palestra palestra);

  /**
   * Mostra todas as palestras contidas no banco de palestras.
   * 
   */
  public void mostrar();

  /**
   * Lê e adiciona ao banco, todas as palestras contidas no arquivo texto informado.
   * @param fileName Caminho para o arquivo texto com as palestras do evento
   */
  public void consomeArquivoPalestra(String fileName);

  /**
   * Informa se o banco de palestras está vazio.
   * 
   * @return Se o banco de palestras está vazio
   */
  public boolean vazio();

  /**
   * Informa o tempo total das palestras contidas no banco de palestras.
   * 
   * @return tempo total das palestras contidas no banco de palestras
   */
  public int getTempoTotal();

  /**
   * Informa a quantidade de palestras contidas no banco de palestras.
   * 
   * @return Quantidade de palestras contidas no banco de palestras
   */
  public int getTotalPalestras();

  /** 
   * Retorna a palestra na posicao solicitada no banco de palestras.
   * 
   * @param posicao Posição em que se encontra a palestra desejada
   * @return Palestra na posicao solicitada
   */
  public Palestra getPalestra(int posicao);

  /**
   * Informa a quantidade de palestras do banco que ainda não fazem parte do evento que está sendo montado.
   * 
   * @return Quantidade de palestras do banco que ainda não fazem parte do evento que está sendo montado
   */
  public int getTotalPalestrasNaoAgendadas();

}