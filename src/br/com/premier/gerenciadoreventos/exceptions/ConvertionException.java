package br.com.premier.gerenciadoreventos.exceptions;

/**
 * Exceção gerada quando um problema em uma conversão acontece
 * 
 * @author fhc
 *
 */
public class ConvertionException extends Exception {

  private static final long serialVersionUID = -5529343139521926832L;
  
  public ConvertionException(String message) {
    super(message);
  }

}
