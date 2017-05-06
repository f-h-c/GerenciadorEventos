package br.com.premier.gerenciadoreventos.converters;

import java.io.IOException;

import br.com.premier.gerenciadoreventos.exceptions.ConvertionException;
import br.com.premier.gerenciadoreventos.infra.PropertiesConsumer;
import br.com.premier.gerenciadoreventos.models.Palestra;

public class PalestraConverter {

  public static Palestra convert(String line) throws ConvertionException {
    String title = "";
    int minutes = 0;
    boolean error = true;
    String errorMsg = "";
    String lightning = "lightning";
    String min = "min";

    try {
      PropertiesConsumer prop = PropertiesConsumer.getInstance("./properties/parametros.properties");
      lightning = prop.getProp("lightning.valorStr", lightning);
      min = prop.getProp("minute.valorStr", min);
    }
    catch (IOException e1) {
      e1.printStackTrace();
    }

    if (line != null) {
      int pos = (line.lastIndexOf(" ") != -1) ? line.lastIndexOf(" ") : line.length();
      int minPos = line.toLowerCase().lastIndexOf(min);
      String minStr = "";

      title = line.substring(0, pos);

      if (minPos == -1) {
        minStr = line.substring(pos).trim();

        if (minStr.equalsIgnoreCase(lightning)) {
          minutes = 5;
          error = false;
        }
      }
      else {
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

    if (error)
      throw new ConvertionException(errorMsg);

    return new Palestra(title, minutes);
  }

  public static void main(String[] args) {

    Palestra t;

    try {
      t = convert("Writing Fast Tests Against Enterprise Rails 60min");

      System.out.println(t);
    }
    catch (ConvertionException e) {
      e.printStackTrace();
    }

    try {
      t = convert("Rails for Python Developers lightning");

      System.out.println(t);
    }
    catch (ConvertionException e) {
      e.printStackTrace();
    }

    try {
      t = convert("null");

      System.out.println(t);
    }
    catch (ConvertionException e) {
      e.printStackTrace();
    }

    try {
      t = convert(null);

      System.out.println(t);
    }
    catch (ConvertionException e) {
      e.printStackTrace();
    }
  }
}
