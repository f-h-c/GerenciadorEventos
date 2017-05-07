package br.com.premier.gerenciadoreventos.converters;

import java.io.IOException;

import br.com.premier.gerenciadoreventos.exceptions.ConvertionException;
import br.com.premier.gerenciadoreventos.infra.PropertiesConsumer;
import br.com.premier.gerenciadoreventos.models.Palestra;

/**
 * Conversor de uma linha de texto em um objeto da classe Palestra.
 * 
 * @author fhc
 *
 */
public class PalestraConverter {

  /**
   * Converte a linha passada, retornando o objeto da classe Palestra convertido.
   * 
   * @param line String com os dados da Palestra
   * @return Objeto da classe Palestra obtido a partir da String passada
   * @throws ConvertionException Exceção gerada quando não foi possível converter a String em objeto da classe Palestra
   */
  public static Palestra convert(String line) throws ConvertionException {
    String title = "";
    int minutes = 0;
    boolean error = true;
    String errorMsg = "";
    String lightning = "lightning";
    String min = "min";

    try {
      PropertiesConsumer prop = PropertiesConsumer.getInstance("./properties/parametros.properties");
      //busca no properties a String que representará palestras lightning
      lightning = prop.getProp("lightning.valorStr", lightning);
      //busca no properties a String que indicará a unidade de minutos nas informações das palestras contidas no arquivo texto passado
      min = prop.getProp("minute.valorStr", min);
    }
    catch (IOException e1) {
      e1.printStackTrace();
    }

    if (line != null) {
      //busca a posição do último espaço em branco da linha lida. Caso não exista, retorna o tamanho total da linha
      int pos = (line.lastIndexOf(" ") != -1) ? line.lastIndexOf(" ") : line.length();
      //busca a posição da unidade de medida de minutos, a fim de obter apenas o valor numérico indicador do tempo total da palestra
      int minPos = line.toLowerCase().lastIndexOf(min);
      String minStr = "";

      //recorta o título da palestra
      title = line.substring(0, pos);

      //verifica se foi encontrado o tempo em minutos
      if (minPos == -1) {
        //recorta a última palavra da linha recebida
        minStr = line.substring(pos).trim();

        //verifica se é uma palestra lightning
        if (minStr.equalsIgnoreCase(lightning)) {
          minutes = 5;
          error = false;
        }
      }
      else {
        //recorta o tempo total da palestra
        minStr = line.substring(pos, minPos).trim();

        try {
          minutes = Integer.parseInt(minStr);

          error = false;
        }
        catch (NumberFormatException e) {
          errorMsg = e.getMessage();
        }
      }
    }

    //Verifica se houve erro durante a conversão
    if (error)
      throw new ConvertionException(errorMsg);

    return new Palestra(title, minutes);
  }
}
