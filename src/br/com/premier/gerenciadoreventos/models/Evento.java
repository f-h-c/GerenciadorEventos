package br.com.premier.gerenciadoreventos.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.premier.gerenciadoreventos.iface.iBancoPalestra;
import br.com.premier.gerenciadoreventos.iface.iEvento;
import br.com.premier.gerenciadoreventos.iface.iPeriodoEvento;
import br.com.premier.gerenciadoreventos.iface.iTrilhaEvento;
import br.com.premier.gerenciadoreventos.infra.PropertiesConsumer;

/** 
 * Administradora do evento a ser criado a partir do arquivo de palestras informado.
 * 
 * @author fhc
 *
 */
public class Evento implements iEvento {
  private String fileName;
  private iBancoPalestra banco = new BancoPalestra();
  private int totalTrilhas = 0;
  private int horasManha = 180;
  private int horasTardeMin = 180;
  private int horasTardeMax = 240;
  private int horaIniManha = 9;
  private int minIniManha = 0;
  private int horaIniTarde = 13;
  private int minIniTarde = 0;
  private List<TrilhaEvento> trilhas = new ArrayList<>();

  private void carregaPropriedades() {
    try {
      PropertiesConsumer prop = PropertiesConsumer.getInstance("./properties/parametros.properties");
      horasManha = Integer.parseInt(prop.getProp("qtde.horas.Manha", Integer.toString(horasManha)));
      horasTardeMin = Integer.parseInt(prop.getProp("qtde.horas.Tarde.Min", Integer.toString(horasTardeMin)));
      horasTardeMax = Integer.parseInt(prop.getProp("qtde.horas.Tarde.Max", Integer.toString(horasTardeMax)));
      horaIniManha = Integer.parseInt(prop.getProp("hora.Inicio.Manha", Integer.toString(horaIniManha)));
      minIniManha = Integer.parseInt(prop.getProp("minuto.Inicio.Manha", Integer.toString(minIniManha)));
      horaIniTarde = Integer.parseInt(prop.getProp("hora.Inicio.Tarde", Integer.toString(horaIniTarde)));
      minIniTarde = Integer.parseInt(prop.getProp("minuto.Inicio.Tarde", Integer.toString(minIniTarde)));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public Evento(String arquivoPaletras) {
    carregaPropriedades();
    
    this.fileName = arquivoPaletras;

    banco.consomeArquivoPalestra(this.fileName);

    //descobre a quantidade de trilhas necessárias para comportar todas as palestras informadas
    this.totalTrilhas = Math.round((float) banco.getTempoTotal() / (horasManha + horasTardeMax));

    Calendar hrIniManha = new GregorianCalendar();
    hrIniManha.set(Calendar.HOUR_OF_DAY, horaIniManha);
    hrIniManha.set(Calendar.MINUTE, minIniManha);
    Calendar hrIniTarde = new GregorianCalendar();
    hrIniTarde.set(Calendar.HOUR_OF_DAY, horaIniTarde);
    hrIniTarde.set(Calendar.MINUTE, minIniTarde);

    //Cria e armazena na lista de trilhas, as trilhas necessárias para comportar as palestras
    for (int i = 0; i < totalTrilhas; i++)
      trilhas.add(new TrilhaEvento(horasManha, hrIniManha, horasTardeMin, horasTardeMax, hrIniTarde));
  }

  /**
   * Monta o período solicitado da trilha de um evento com as palestras constantes no banco de palestras.
   * 
   * @param trilha
   * @param p
   */
  private void montaPeriodoDia(iTrilhaEvento trilha, PeriodoTrilha p) {

    int totalPalestras = banco.getTotalPalestras();
    iPeriodoEvento periodo = null;
    int cont = 0;

    //percorre todo o banco de palestras tentando montar uma combinação de palestras que se encaixe nas regras do período da trilha
    do {
      int inicio = cont;
      //solicita à trilha que forneça uma instância do período com todas as suas respectivas regras
      periodo = trilha.getInstance(p);

      //tenta montar o período a partir da combinação das palestras disponíveis no banco de palestras.
      //sairá do laço quando tiver testado todas as palestras ou quando a montagem estiver de acordo com as regras do período
      while ((inicio != totalPalestras) && (!periodo.tempoOk())) {
        int contAtual = inicio;
        inicio++;
        Palestra palestra = banco.getPalestra(contAtual);
        //tenta adicionar a palestra ao período
        periodo.addPalestra(palestra);
      }

      //verifica se o período montado está de acordo com as regras
      if (periodo.tempoOk())
        //adiciona o período na trilha
        trilha.setPeriodo(p, periodo);
      else
        cont++;

    } while ((cont < totalPalestras) && (!periodo.tempoOk())); 
    //sairá do laço quando tiver percorrido todas as palestras do banco ou quando conseguiu preencher o período com as palestras de acordo com as regras período
  }

  /**
   * Complementa os períodos das trilhas de um evento com as palestras que sobraram.
   * 
   * @param trilha
   * @param p
   */
  private void complementaPeriodoDia(iTrilhaEvento trilha, PeriodoTrilha p) {
    //obtém o período definido da trilha passada
    iPeriodoEvento periodo = trilha.getPeriodo(p);

    //percorre o banco de palestras, tentando encaixar as palestras que não foram agendadas, no período da trilha
    for (int i = 0; i < banco.getTotalPalestras(); i++) {
      Palestra palestra = banco.getPalestra(i);

      //Tenta adicionar a palestra no período, se conseguir, deve marcar a palestra como agendada
      if (periodo.addPalestra(palestra))
        palestra.agendada(true);
    }
  }

  @Override
  public boolean montaEvento() {
    for (iTrilhaEvento dia : trilhas)
      montaPeriodoDia(dia, PeriodoTrilha.MANHA);

    for (iTrilhaEvento dia : trilhas)
      montaPeriodoDia(dia, PeriodoTrilha.TARDE);

    for (iTrilhaEvento dia : trilhas)
      complementaPeriodoDia(dia, PeriodoTrilha.TARDE);

    return banco.getTotalPalestrasNaoAgendadas() == 0;

  }

  @Override
  public void mostrar() {
    int track = 1;

    for (iTrilhaEvento dia : trilhas) {
      System.out.println("Track " + track++ + ":");
      dia.mostrar();
      System.out.println("");
    }
  }

}
