package br.com.premier.gerenciadoreventos.infra;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Classe para facilitar a leitura das informações dos arquivos properties
 * 
 * @author fhc
 *
 */
public class PropertiesHelper {
  private Properties prop = new Properties();
  //Ajudará na implementação do Singleton, uma vez que retornará sempre a mesma instância de um determinado arquivo de properties
  private static Map<String, PropertiesHelper> props = new HashMap<>();

  /**
   * Construtor da classe exige o nome do arquivo de properties para poder carregá-lo.
   * 
   * @param fileName Nome do arquivo de properties
   * @throws IOException Lançada quando dá algum problema ao tentar abrir o arquivo de properties
   */
  public PropertiesHelper(String fileName) throws IOException {
    FileInputStream file = new FileInputStream(fileName);

    prop.load(file);
  }

  /**
   * Fornece o valor da propriedade no arquivo passado inicialmente a partir da chave de busca.
   * 
   * @param chave Chave de busca para a propriedade desejada
   * @param valorPadrao Valor padrão caso a chave não seja encontrada
   * @return Valor da propriedade encontrada para a chave fornecida
   */
  public String getProp(String chave, String valorPadrao) {
    return prop.getProperty(chave, valorPadrao);
  }
  
  /**
   * Implementa o Singleton. 
   * Verifica se já existe uma instância da classe para o arquivo solicitado. Se já existir a retorna, senão a cria antes de retornar.
   * 
   * @param fileName
   * @return
   * @throws IOException
   */
  public static PropertiesHelper getInstance(String fileName) throws IOException {
    PropertiesHelper result = null;
    
    result = props.get(fileName.toLowerCase());
    
    if (result == null) {
      result = new PropertiesHelper(fileName);
      
      props.put(fileName.toLowerCase(), result);    
    }
    
    return result;
  }
}