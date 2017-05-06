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

public class Evento implements iEvento {
  private String fileName;
  private iBancoPalestra banco = new BancoPalestra();
  private int totalDias = 0;
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

    this.totalDias = Math.round((float) banco.getTempoTotal() / (horasManha + horasTardeMax));

    Calendar hrIniManha = new GregorianCalendar();
    hrIniManha.set(Calendar.HOUR_OF_DAY, horaIniManha);
    hrIniManha.set(Calendar.MINUTE, minIniManha);
    Calendar hrIniTarde = new GregorianCalendar();
    hrIniTarde.set(Calendar.HOUR_OF_DAY, horaIniTarde);
    hrIniTarde.set(Calendar.MINUTE, minIniTarde);

    for (int i = 0; i < totalDias; i++)
      trilhas.add(new TrilhaEvento(horasManha, hrIniManha, horasTardeMin, horasTardeMax, hrIniTarde));
  }

  private void montaPeriodoDia(iTrilhaEvento dia, PeriodoDia p) {

    int totalPalestras = banco.getTotalPalestras();
    iPeriodoEvento periodo = null;
    int cont = 0;

    do {
      int inicio = cont;
      periodo = dia.getInstance(p);

      while ((inicio != totalPalestras) && (!periodo.tempoOk())) {
        int contAtual = inicio;
        inicio++;
        Palestra palestra = banco.getPalestra(contAtual);
        periodo.addPalestra(palestra);
      }

      if (periodo.tempoOk())
        dia.setPeriodo(p, periodo);
      else
        cont++;

    } while ((cont < totalPalestras) && (!periodo.tempoOk()));
  }

  private void complementaPeriodoDia(iTrilhaEvento dia, PeriodoDia p) {
    iPeriodoEvento periodo = dia.getPeriodo(p);

    for (int i = 0; i < banco.getTotalPalestras(); i++) {
      Palestra palestra = banco.getPalestra(i);

      if (periodo.addPalestra(palestra))
        palestra.agendada(true);
    }
  }

  @Override
  public boolean montaEvento() {
    for (iTrilhaEvento dia : trilhas)
      montaPeriodoDia(dia, PeriodoDia.MANHA);

    for (iTrilhaEvento dia : trilhas)
      montaPeriodoDia(dia, PeriodoDia.TARDE);

    for (iTrilhaEvento dia : trilhas)
      complementaPeriodoDia(dia, PeriodoDia.TARDE);

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

  public static void main(String[] args) {
    iEvento e = new Evento("/input.txt");

    if (e.montaEvento())
      e.mostrar();
    else
      System.out.println("Não foi possível alocar todas as palestras da forma pré-estabelecida.");
  }

}
