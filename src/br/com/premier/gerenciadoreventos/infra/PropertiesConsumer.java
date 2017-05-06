package br.com.premier.gerenciadoreventos.infra;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesConsumer {
  private Properties prop = new Properties();
  private static Map<String, PropertiesConsumer> props = new HashMap<>();

  public PropertiesConsumer(String fileName) throws IOException {
    FileInputStream file = new FileInputStream(fileName);

    prop.load(file);
  }

  public String getProp(String chave, String valorPadrao) {
    return prop.getProperty(chave, valorPadrao);
  }
  
  public static PropertiesConsumer getInstance(String fileName) throws IOException {
    PropertiesConsumer result = null;
    
    result = props.get(fileName.toLowerCase());
    
    if (result == null) {
      result = new PropertiesConsumer(fileName);
      
      props.put(fileName.toLowerCase(), result);    
    }
    
    return result;
  }
  
  public static void main(String[] args) {
    try {
      PropertiesConsumer cons = getInstance("./properties/parametros.properties");
      
      System.out.println(cons.getProp("formato.hora", "num tem"));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
