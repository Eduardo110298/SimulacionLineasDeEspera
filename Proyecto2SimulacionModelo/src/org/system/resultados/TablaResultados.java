/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.system.resultados;

import java.awt.Color;
import java.awt.Toolkit;
import org.thread.hilos.HiloEstadisticasFinales;
import org.system.inicio.FormularioEntrada;
import org.system.patrones.Formulas;
import org.system.random.generator.Rand;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.timer.temporizador.SystemTimer;

/*
 Interfaz donde se generan los resultados y las estadísticas finales
 */
public class TablaResultados extends javax.swing.JFrame {

    //Atributos de la clase
    protected Rand rnd = null;
    protected ArrayList<ArrayList<String>> resultadosTabla = new ArrayList<>();
    protected ArrayList<Double> patronLlegadaClientes = new ArrayList<>();
    protected ArrayList<Double> patronServicioClientes = new ArrayList<>();
    protected ArrayList<Double> tiempoEsperaCola = new ArrayList<>();
    protected ArrayList<String> formulaLlegadaClientes = new ArrayList<>();
    protected ArrayList<String> formulaServicioClientes = new ArrayList<>();
    protected ArrayList<String> eventos = new ArrayList<>();       
    protected double E1 = -1;
    protected double E2 = -1;
    protected double E3 = -1;
    protected double tiempoTotal = 0;
    protected double tiempoPromedioLlegada = 0;
    protected double tiempoPromedioServicio = 0;
    protected double tiempoPromedioEsperaCola = 0;
    protected double tiempoPromedioEsperaSistema = 0;
    protected double tiempoOcioServidor = 0;
    protected double tiempoUtilizacionServidor = 0;
    protected int NE = 0;
    protected int NEMaximo = 0;
    protected int contLlegadaClientes = 0;
    protected int contServicioClientes = 0;
    protected int contPatronLlegada = 0;
    protected int contPatronServicio = 0;
    protected final int _INT_MAX_VALUE = 725;
    protected boolean estadisticasGeneradas = false;
    private SystemTimer systemTimer = null;

    /*
        Método donde se redondea un valor decimal a n dígitos
    */
    double round (double value, int digits) {
        final int lengthDigits = (int) Math.pow(10, digits);
        return (double) (Math.round(value * (double) lengthDigits) / (double) lengthDigits);
    }
    
    /*
        Método en el que se obtiene un número con decimales en su respectivo formato
    */
    String formatoDecimal(double value) {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator(',');
        DecimalFormat formato = new DecimalFormat("0.00", simbolos);
        return formato.format(value);
    }
    
    /*
        Método donde se ordena la lista de eventos de forma ascendente
    */
    void ordenarEventos() {
        String[] listaEventos = new String[3];
        eventos = new ArrayList<>();

        if (E1 <= E2 && E1 <= E3) {
            listaEventos[0] = "E1/" + String.valueOf(E1);
            if (E2 <= E3) {
                listaEventos[1] = "E2/" + String.valueOf(E2);
                listaEventos[2] = "E3/" + String.valueOf(E3);
            } else {
                listaEventos[1] = "E3/" + String.valueOf(E3);
                listaEventos[2] = "E2/" + String.valueOf(E2);
            }
        } else {
            if (E2 <= E1 && E2 <= E3) {
                listaEventos[0] = "E2/" + String.valueOf(E2);
                if (E1 <= E3) {
                    listaEventos[1] = "E1/" + String.valueOf(E1);
                    listaEventos[2] = "E3/" + String.valueOf(E3);
                } else {
                    listaEventos[1] = "E3/" + String.valueOf(E3);
                    listaEventos[2] = "E1/" + String.valueOf(E1);
                }
            } else {
                listaEventos[0] = "E3/" + String.valueOf(E3);
                if (E1 <= E2) {
                    listaEventos[1] = "E1/" + String.valueOf(E1);
                    listaEventos[2] = "E2/" + String.valueOf(E2);
                } else {
                    listaEventos[1] = "E2/" + String.valueOf(E2);
                    listaEventos[2] = "E1/" + String.valueOf(E1);
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            double tmpEvt = round(Double.parseDouble(listaEventos[i].split("/")[1]), 2);
            if (tmpEvt != -1) {
                eventos.add(listaEventos[i]);
            }
        }
    }

    /*
        Método donde se verifica si un evento dado se encuentra registrado en la lista de eventos
    */
    boolean findEvent(String event) {
        return (eventos.stream().anyMatch((evento) -> (evento.split("/")[0].equalsIgnoreCase(event))));
    }

    /*
        Método que genera la lista de eventos ordenados de forma ascendente
    */
    String LE() {
        String listaEvt = "";
        for (int i = 0; i < eventos.size(); i++) {
            if (i > 0) {
                listaEvt += " ";
            }
            listaEvt += eventos.get(i);
        }
        if (listaEvt.isEmpty()) {
            listaEvt = "-";
        }
        return listaEvt;
    }

    /*
        Método donde se verifica si finalizó la corrida de Simulación a través de la condición de parada
    */
    boolean finSimulacion() {

        if (resultadosTabla.isEmpty()) {
            return true;
        }

        if (org.system.inicio.FormularioEntrada.E3 != -1) {
            String condFinal = resultadosTabla.get(resultadosTabla.size() - 1).get(0);
            if (!condFinal.equals("-")) {
                if (Double.parseDouble(condFinal) == org.system.inicio.FormularioEntrada.E3 && !eventos.isEmpty() && eventos.get(0).split("/")[0].equals("E3")) {
                    return false;
                } 
                return Double.parseDouble(condFinal) == org.system.inicio.FormularioEntrada.E3;
            } else {
                return false;
            }
        } else {
            if (org.system.inicio.FormularioEntrada.LimiteLlegada != -1) {
                return org.system.inicio.FormularioEntrada.LimiteLlegada == contLlegadaClientes;
            } else {
                return org.system.inicio.FormularioEntrada.LimiteServicio == contServicioClientes;
            }
        }
        
    }
    
    /*
        Método donde se muestra un mensaje de advertencia
    */
    public void mostrarMensajeAdvertencia(String title, String message) {
            
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.WARNING_MESSAGE);
        
    }
    
    /*
        Método donde se verifica si se generaron números pseudoaleatorios repetidos
        en la corrida de Simulación
    */
    public boolean numerosPseudoaleatoriosRepetidos() {
        
        Map<Double, Double> randMap = new HashMap<>();
        
        for (ArrayList<String> resultadosTabla1 : resultadosTabla) {
            if (!resultadosTabla1.get(3).equals("-")) {
                double tmpRandValue = Double.parseDouble(resultadosTabla1.get(3));
                if (!randMap.isEmpty() && randMap.containsValue(tmpRandValue)) {
                    return true;
                }
                randMap.put(tmpRandValue, tmpRandValue);
            }
        }
        
        return false;
        
    }
    
    /*
        Método en el que se muestra la fórmula del patrón de llegada en su respectivo formato
    */
    String mostrarFormulaLlegada(int i, int op) {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator(',');
        DecimalFormat formato = new DecimalFormat("0.00", simbolos);
        String formula = op==1? "X <sub>" + String.valueOf(i+1) + "</sub> = " + formulaLlegadaClientes.get(i):
                "X <sub>" + String.valueOf(i+1) + "</sub> = " + formato.format(patronLlegadaClientes.get(i)) + "m";
        
        String result =   "<html>"
                        +   "<body>"
                        +      formula
                        +   "</body>"
                        + "</html>";
        
        return result;
    }
    
    /*
        Método en el que se muestra la fórmula del patrón de servicio en su respectivo formato
    */
    String mostrarFormulaServicio(int i, int op) {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator(',');
        DecimalFormat formato = new DecimalFormat("0.00", simbolos);
        String formula = op==1? "X <sub>" + String.valueOf(i+1) + "</sub> = " + formulaServicioClientes.get(i):
                "X <sub>" + String.valueOf(i+1) + "</sub> = " + formato.format(patronServicioClientes.get(i)) + "m";
        
        String result =   "<html>"
                        +   "<body>"
                        +      formula
                        +   "</body>"
                        + "</html>";
        
        return result;
    }
    
    /*
        Método en el que se llena la lista del patrón de servicio
    */
    public void mostrarPatronServicio() {
        DefaultListModel modelo = new DefaultListModel();
        for (int i = 0; i < formulaServicioClientes.size(); i++) {
            modelo.addElement(mostrarFormulaServicio(i, 1));
            modelo.addElement(mostrarFormulaServicio(i, 2));
        }
        PatronesServicio.setModel(modelo);
        PatronesServicio.validate();
        PatronesServicio.repaint();
    }
    
    /*
        Método en el que se llena la lista del patrón de llegada
    */
    public void mostrarPatronLlegada() {
        DefaultListModel modelo = new DefaultListModel();
        for (int i = 0; i < formulaLlegadaClientes.size(); i++) {
            modelo.addElement(mostrarFormulaLlegada(i, 1));
            modelo.addElement(mostrarFormulaLlegada(i, 2));
        }
        PatronesLlegada.setModel(modelo);
        PatronesLlegada.validate();
        PatronesLlegada.repaint();
    }
    
    /*
        Método donde se obtiene el nombre del evento en su respectivo formato
    */
    String formatoEvento(String evt) {
        String result = "-";
        if (evt.equals("E1")) {
            result = "E <sub> 1 </sub>";
        } else {
            if (evt.equals("E2")) {
                result = "E <sub> 2 </sub>";
            } else {
                if (evt.equals("E3")) {
                    result = "E <sub> 3 </sub>";
                }
            }
        }
        return result;
    }
    
    /*
        Método donde se obtiene el formato del T.R.
    */
    String formatoTR(int i) {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator(',');
        DecimalFormat formato = new DecimalFormat("0.00", simbolos);
        String result = resultadosTabla.get(i).get(0).equals("-")? "-":
                formato.format(Double.parseDouble(resultadosTabla.get(i).get(0))) + "m";
        return result;
    }
    
    /*
        Método donde se obtiene el formato del E.Q.
    */
    String formatoEQ(int i) {
        String evt = resultadosTabla.get(i).get(1);
        String result = "-";
        if (evt.equals("E1")) {
            result = "<html> <body> E <sub> 1 </sub> </body> </html>";
        } else {
            if (evt.equals("E2")) {
                result = "<html> <body> E <sub> 2 </sub> </body> </html>";
            } else {
                if (evt.equals("E3")) {
                    result = "<html> <body> E <sub> 3 </sub> </body> </html>";
                }
            }
        }
        return result;
    }
    
    /*
        Método donde se obtiene el formato del N.E.
    */
    String formatoNE(int i) {
        return resultadosTabla.get(i).get(2);
    }
    
    /*
        Método donde se obtiene el formato del Rn
    */
    String formatoRn(int i) {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator(',');
        DecimalFormat formato = new DecimalFormat("0.000", simbolos);
        String result = resultadosTabla.get(i).get(3).equals("-")? "-":
                formato.format(Double.parseDouble(resultadosTabla.get(i).get(3)));
        return result;
    }
    
    /*
        Método donde se obtiene el formato del E.G.
    */
    String formatoEG(int i) {
        String evt = resultadosTabla.get(i).get(4);
        String result = "-";
        if (!evt.equals("-") && !evt.isEmpty()) {
            DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
            simbolos.setDecimalSeparator(',');
            DecimalFormat formato = new DecimalFormat("0.00", simbolos);
            double value = Double.parseDouble(evt.split("/")[1]);
            String event = evt.split("/")[0];
            result = "<html> <body> " + formatoEvento(event);
            result += "/" + formato.format(value) + "m </body> </html>";
        }
        return result;
    }
    
    /*
        Método donde se obtiene el formato del L.E.
    */
    String formatoLE(int i) {
        String evt = resultadosTabla.get(i).get(5);
        String result = "-";
        if (!evt.equals("-") && !evt.isEmpty()) {
            DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
            simbolos.setDecimalSeparator(',');
            DecimalFormat formato = new DecimalFormat("0.00", simbolos);
            String [] listaEvt = evt.split(" ");
            result = "<html> <body>";
            for (int j = 0; j < listaEvt.length; j++) {
                if (j > 0) {
                    result += " ";
                }
                double value = Double.parseDouble(listaEvt[j].split("/")[1]);
                String event = listaEvt[j].split("/")[0];
                result += formatoEvento(event);
                result += "/" + formato.format(value) + "m";
            }
            result += "</body> </html>";
        }
        return result;
    }
    
    /*
        Método donde se llena la tabla con los resultados de la corrida de Simulación
    */
    public void mostrarTablaResultados() {
        DefaultTableModel modelo = (DefaultTableModel) TablaResultadosFinales.getModel();
        for (int i = 0; i < resultadosTabla.size(); i++) {
            Object[] fila = new Object[modelo.getColumnCount()];
            fila[0] = formatoTR(i);
            fila[1] = formatoEQ(i);
            fila[2] = formatoNE(i);
            fila[3] = formatoRn(i);
            fila[4] = formatoEG(i);
            fila[5] = formatoLE(i);
            modelo.addRow(fila);
        }
        TablaResultadosFinales.validate();
        TablaResultadosFinales.repaint();
    }

    /*
        Método donde se verifica si se debe agregar en el tiempo de servicio al primer cliente.
        Esto ocurre si en las condiciones iniciales se suministran los tiempos de los próximos
        eventos de dicho cliente en caso de que N.E. sea igual a 0. En caso contrario, con N.E.
        diferente de 0, ocurre si se suministra el tiempo del próximo servicio.
    */
    boolean agregarPrimerCliente() {
        
        if (org.system.inicio.FormularioEntrada.NroEntidades == 0 && org.system.inicio.FormularioEntrada.E1!=-1 && org.system.inicio.FormularioEntrada.E2!=-1 && !resultadosTabla.isEmpty()) {
            if (!resultadosTabla.get(resultadosTabla.size()-1).get(0).equals("-")) {
                double tmpTR = Double.parseDouble(resultadosTabla.get(resultadosTabla.size()-1).get(0));
                if (org.system.inicio.FormularioEntrada.E2 <= tmpTR) {
                    return true;
                }
            }
        }
        
        if (org.system.inicio.FormularioEntrada.NroEntidades != 0  && org.system.inicio.FormularioEntrada.E2!=-1 && !resultadosTabla.isEmpty()) {
            if (!resultadosTabla.get(resultadosTabla.size()-1).get(0).equals("-")) {
                double tmpTR = Double.parseDouble(resultadosTabla.get(resultadosTabla.size()-1).get(0));
                if (org.system.inicio.FormularioEntrada.E2 <= tmpTR) {
                    return true;
                }
            }
        }
        
        return false;
        
    }
    
    /*
        Método donde se genera el tiempo promedio de espera en el Sistema
    */
    public void generarTiempoPromedioEsperaSistema() {
        
        double tiempoPromedio = 0;
        int nServicios = tiempoEsperaCola.size();
        boolean flagPrimerCliente = false;
        if (agregarPrimerCliente()) {
            flagPrimerCliente = true;
            tiempoPromedio += (org.system.inicio.FormularioEntrada.NroEntidades == 0)?
                    (org.system.inicio.FormularioEntrada.E2 - org.system.inicio.FormularioEntrada.E1) : org.system.inicio.FormularioEntrada.E2;
        }
        
        boolean flagProximoE2 = false;
        if (!flagPrimerCliente) {
            if (org.system.inicio.FormularioEntrada.E2!=-1) {
                if (nServicios!=0) {
                    flagProximoE2 = true;
                    tiempoPromedio += org.system.inicio.FormularioEntrada.E2;
                }
            }
        }
        
        int pos = 0;
        
        for (Double tiempoEsperaCola1 : tiempoEsperaCola) {
            if (flagPrimerCliente) {
                tiempoPromedio += tiempoEsperaCola1;
                flagPrimerCliente = false;
            } else {
                if (flagProximoE2) {
                    tiempoPromedio += tiempoEsperaCola1;
                    flagProximoE2 = false;
                } else {
                    tiempoPromedio += (pos < patronServicioClientes.size()? patronServicioClientes.get(pos) : 0) + tiempoEsperaCola1;
                    pos++;
                }
            }
        }
        
        if (nServicios > 0) {
            tiempoPromedio/=(double)nServicios;
        }
        
        tiempoPromedioEsperaSistema = tiempoPromedio;
        
    }
    
    /*
        Método donde se ubica el tiempo a partir del cual deja de esperar el cliente para ser atendido
    */
    double tiempoEsperaCliente (int j, int ne) {
        double result = -1;
        for (int i = j; i < resultadosTabla.size(); i++) {
            if (resultadosTabla.get(i).get(1).equalsIgnoreCase("E2")) {
                ne--;
                if (ne <= 1) {
                    result = resultadosTabla.get(i).get(0).equals("-")? 0 : Double.parseDouble(resultadosTabla.get(i).get(0));
                    break;
                }
            }
        }
        return result;
    }
    
    /*
        Método donde se obtiene el tiempo del E2 que corresponde para que culmine el tiempo de espera en cola del
        n-ésimo cliente
    */
    int obtenerE2(int n) {
        for (int i = 0; i < resultadosTabla.size(); i++) {
            if (resultadosTabla.get(i).get(1).equalsIgnoreCase("E2")) {
                n--;
                if (n <= 0) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    /*
        Método donde se genera el tiempo promedio de espera en cola
    */
    public void generarTiempoPromedioEsperaCola() {
        
        int cantClientes = contServicioClientes, nCliente = 0;
        
        if (cantClientes == 0) {
            return;
        }
        
        double tiempoPromedio = 0;
        
        int ne = org.system.inicio.FormularioEntrada.NroEntidades;
        
        if (ne != 0) {
            tiempoEsperaCola.add(0.00);
            nCliente++;
            ne--;
            for (int i = 1; i <= ne; i++) {
                if (nCliente >= cantClientes) {
                    break;
                }
                int pos = obtenerE2(i);
                if (pos!=-1) {
                    tiempoEsperaCola.add(Double.parseDouble(resultadosTabla.get(pos).get(0)));
                    tiempoPromedio += Double.parseDouble(resultadosTabla.get(pos).get(0));
                    nCliente++;
                } else {
                    break;
                }
            }
        }
        
        for (int i = 0; i < resultadosTabla.size(); i++) {
            if (nCliente >= cantClientes) {
                break;
            }
            if (resultadosTabla.get(i).get(1).equalsIgnoreCase("E1")) {
                nCliente++;
                ne = resultadosTabla.get(i).get(2).equals("-")? 0 : Integer.parseInt(resultadosTabla.get(i).get(2));
                if (ne <= 1) {
                    tiempoEsperaCola.add(0.00);
                } else {
                    double tmpTiempoCliente = resultadosTabla.get(i).get(0).equals("-")? 0 : Double.parseDouble(resultadosTabla.get(i).get(0));
                    double tmpTiempoEspera = tiempoEsperaCliente(i + 1, ne);
                    if (tmpTiempoEspera != -1) {
                        double tiempoDeEspera = tmpTiempoEspera-tmpTiempoCliente;
                        double tiempoEsperaColaCliente = tiempoDeEspera < 0 ? 0 : tiempoDeEspera;
                        tiempoEsperaCola.add(tiempoEsperaColaCliente);
                        tiempoPromedio += tiempoEsperaColaCliente;
                    }
                }
            }
        }
        
        cantClientes = tiempoEsperaCola.size();
        
        if (cantClientes > 0) {
            tiempoPromedio /= (double)cantClientes;
        }
        
        tiempoPromedioEsperaCola = tiempoPromedio;
        
    }
    
    /*
        Método donde se genera el tiempo de utilización del servidor
    */
    public void generarTiempoUtilizacionServidor() {
        
        double tiempoUtilizado = tiempoTotal - tiempoOcioServidor;
        tiempoUtilizacionServidor = tiempoUtilizado<0? 0 : tiempoUtilizado;
        
    }
    
    /*
        Método donde se genera el tiempo total de la corrida de Simulación
    */
    public void generarTiempoTotal() {
        if (!resultadosTabla.isEmpty()) {
            int lengthResultados = resultadosTabla.size()-1;
            String resultadosTR = resultadosTabla.get(lengthResultados).get(0);
            double tmpTiempo = resultadosTR.equals("-")? 0 : Double.parseDouble(resultadosTR);
            tiempoTotal = tmpTiempo;
        }
    }
    
    /*
        Método donde se ubica el próximo tiempo del reloj donde ocurre algún evento
    */
    double proximoTiempo(int j) {
        for (int i = j; i < resultadosTabla.size(); i++) {
            int ne = resultadosTabla.get(i).get(2).equals("-")? 0 : Integer.parseInt(resultadosTabla.get(i).get(2));
            if (ne != 0) {
                return resultadosTabla.get(i).get(0).equals("-")? 0 : Double.parseDouble(resultadosTabla.get(i).get(0));
            }
        }
        return (!resultadosTabla.isEmpty() && !resultadosTabla.get(resultadosTabla.size()-1).get(0).equals("-"))?
                Double.parseDouble(resultadosTabla.get(resultadosTabla.size()-1).get(0)) : 0;
    }
    
    /*
        Método donde se genera el tiempo de ocio del servidor
    */
    public void generarTiempoOcioServidor() {
        
        double tiempoOcio = 0;
        for (int i = 0; i < resultadosTabla.size(); i++) {
            if (!resultadosTabla.get(i).get(2).equals("-")) {
                int ne = Integer.parseInt(resultadosTabla.get(i).get(2));
                double tmpTiempo = resultadosTabla.get(i).get(0).equals("-")? 0 : Double.parseDouble(resultadosTabla.get(i).get(0));
                if (ne == 0) {
                    double tmpProximoTiempo = proximoTiempo(i+1);
                    if (tmpProximoTiempo - tmpTiempo >= 0) {
                        tiempoOcio += (tmpProximoTiempo-tmpTiempo);
                    }
                }
            }
        }
        
        tiempoOcioServidor = tiempoOcio;
    }
    
    /*
        Método donde se genera el tiempo promedio de servicio
    */
    public void generarTiempoPromedioServicio() {
        
        double tiempoPromedio = 0;
        int nServicios = contPatronServicio;
        
        if (agregarPrimerCliente()) {
            nServicios++;
            tiempoPromedio += (org.system.inicio.FormularioEntrada.NroEntidades == 0)?
                    (org.system.inicio.FormularioEntrada.E2 - org.system.inicio.FormularioEntrada.E1) : org.system.inicio.FormularioEntrada.E2;
        }

        for (int i = 0; i < contPatronServicio; i++) {
            tiempoPromedio += patronServicioClientes.get(i);
        }
        
        if (nServicios>0) {
            tiempoPromedio/=(double)nServicios;
        }
        
        tiempoPromedioServicio = tiempoPromedio;
        
    }
    
    /*
        Método donde se genera el tiempo promedio de llegada
    */
    public void generarTiempoPromedioLlegada() {
        double tiempoPromedio = 0;
        for (int i = 0; i < contPatronLlegada; i++) {
            tiempoPromedio += patronLlegadaClientes.get(i);
        }
        
        if (contPatronLlegada>0) {
            tiempoPromedio/=(double)contPatronLlegada;
        }
        
        tiempoPromedioLlegada = tiempoPromedio;
    }
    
    /*
        Método donde se genera el número máximo de entidades en la corrida de Simulación
    */
    public void generarNEMaximo() {
        for (ArrayList<String> resultadosTabla1 : resultadosTabla) {
            if (!resultadosTabla1.get(2).equals("-")) {
                int tmpNE = Integer.parseInt(resultadosTabla1.get(2));
                if (tmpNE > NEMaximo) {
                    NEMaximo = tmpNE;
                }
            }
        }
    }
    
    /*
        Método donde se generan las estadísticas finales (contadores y sumarios).
    
        ESTADÍSTICAS
        
        1) Número de entradas efectuadas.
        2) Número de clientes atendidos.
        3) Número máximo de clientes en cola.
        4) Número máximo de clientes en el Sistema.
        5) Tiempo promedio entre llegadas.
        6) Tiempo promedio de servicio.
        7) Tiempo de ocio del Servidor.
        8) Tiempo de utilización del Servidor.
        9) Tiempo promedio de espera en cola.
       10) Tiempo promedio de espera en el Sistema.
    */
    private void generarEstadisticas() {
        if (estadisticasGeneradas) {
            mostrarEstadisticasFinales();
            return;
        }
        /*
            MULTIHILOS
            
            Se crea un nuevo hilo perteneciente a la clase TablaResultados. Consultar clase 
            HiloEstadisticasFinales del paquete org.thread.hilos
        */
        HiloEstadisticasFinales nuevoHilo = new HiloEstadisticasFinales(this);
        nuevoHilo.start();
    }
    
    /*
        Método donde se realizan los cálculos de la corrida de Simulación luego de establecer las condiciones iniciales.
        Se verifica primeramente si se ha llegado a la condición de parada. En caso de no ser así, se procede a evaluar
        el evento que ocurre, y dependiendo de cuál sea el evento, se sigue el determinado procedimiento.
        
        PROCEDIMIENTO
    
        Nota 1. La ocurrencia de un evento de llegada casi siempre dispara la generación del próximo evento de llegada y
        además, podría disparar la generación del próximo evento de culminación de servicio si y solo si éste no se
        encuentra registrado en la lista de eventos y el número de entidades en el Sistema (N.E.) es diferente de 0.
        
        Nota 2. La ocurrencia de un evento de culminación de servicio casi siempre podría disparar la generación del próximo
        evento que indique la culminación de servicio si y solo si se cumple la condición de que N.E. es diferente de 0.
        Además podría disparar la generación del próximo evento de llegada de un cliente si éste no se encuentra registrado
        en la lista de eventos.
    
        Nota 3. Cuando no se proporcione información de los próximos eventos (tiempo de ocurrencia) se debe primeramente
        verificar si N.E. (número de entidades en el Sistema) es igual o diferente de 0. Si N.E. es igual a 0, en el tiempo
        0 (tiempo del reloj) se genera el próximo evento de llegada (asociado al patrón de llegada). Luego se adelanta el
        tiempo del reloj para que ocurra este evento y a partir de aquí se aplica la regla de la nota 1. Si es el caso de
        que N.E. es diferente de 0, en el tiempo 0 se genera primeramente el evento asociado al patrón de llegada y en la
        siguiente línea, en el mismo tiempo 0 se genera el evento asociado al patrón de servicio. Se adelanta el reloj al
        próximo evento a ocurrir y desde aquí se continúa la corrida aplicando la regla correspondiente. Cabe destacar, que
        esta nota se aplica en el método de las condiciones iniciales (ver método condicionesIniciales()).
    
        RESUMEN
    
        Nota 1. Al ocurrir un evento de llegada (E1), se verifica si se ha llegado al final de la corrida de simulación, de
        no ser así, se genera el próximo E1, y en caso de no haber un E2 en la L.E., se genera el próximo E2.
    
        Nota 2. Al ocurrir un evento de culminación de servicio (E2), se verifica si se ha llegado a las condiciones finales, de
        no ser así, en caso de que N.E. sea distinto de 0, se genera el próximo E2, y en caso de no haber un E1 en la L.E., se
        genera el próximo E1.
    
        Nota 3. Si en las condiciones iniciales no se suministraron los tiempos de los próximos eventos, se verifica el valor de
        N.E. Si su valor es 0, entonces en el tiempo 0, se genera el próximo E1, y se sigue con la simulación. En caso contrario,
        en el tiempo 0, se genera el próximo E1 y el próximo E2, y se sigue con la simulación.
    */
    public void calcularResultados() {

        while (!finSimulacion()) {
            
            ordenarEventos();
            
            ArrayList<String> condInic = new ArrayList<>();

            String strTmpEvt = eventos.get(0).split("/")[0];
            double tmpEvt = round(Double.parseDouble(eventos.get(0).split("/")[1]),2);
            condInic.add(String.valueOf(tmpEvt));
            condInic.add(strTmpEvt);
            if (strTmpEvt.equalsIgnoreCase("E1")) { //Nota 1
                if (!patronLlegadaClientes.isEmpty()) {
                    contPatronLlegada++;
                }
                E1 = -1;
                ordenarEventos();
                NE++;
                contLlegadaClientes++;
                condInic.add(String.valueOf(NE));
                if (contLlegadaClientes == FormularioEntrada.LimiteLlegada || FormularioEntrada.E3==tmpEvt) {
                    condInic.add("-");
                    condInic.add("-");
                    condInic.add(LE());
                    resultadosTabla.add(condInic);
                } else {
                    double rn = rnd.nextRandom();
                    condInic.add(String.valueOf(rn));

                    double pattern = org.system.patrones.Formulas.patronLlegada(rn);

                    String formatoFormula = Formulas.formatoPatronLlegada(rn, 3, 6);

                    formulaLlegadaClientes.add(formatoFormula);
                    patronLlegadaClientes.add(pattern);

                    condInic.add("E1/" + String.valueOf(round(pattern + tmpEvt, 2)));
                    E1 = round(pattern + tmpEvt, 2);
                    ordenarEventos();
                    condInic.add(LE());
                    resultadosTabla.add(condInic);

                    if (!findEvent("E2") && NE != 0) {
                        condInic = new ArrayList<>();
                        condInic.add("-");
                        condInic.add("-");
                        condInic.add("-");
                        rn = rnd.nextRandom(_INT_MAX_VALUE);
                        condInic.add(String.valueOf(rn));

                        pattern = org.system.patrones.Formulas.patronServicio(rn);
                        
                        formatoFormula = Formulas.formatoPatronServicio(rn, 3, 6);

                        formulaServicioClientes.add(formatoFormula);
                        patronServicioClientes.add(pattern);

                        condInic.add("E2/" + String.valueOf(round(pattern + tmpEvt, 2)));
                        E2 = round(pattern + tmpEvt, 2);
                        ordenarEventos();
                        condInic.add(LE());
                        resultadosTabla.add(condInic);
                    }
                }
            } else {
                if (strTmpEvt.equalsIgnoreCase("E2")) { //Nota 2
                    if (!patronServicioClientes.isEmpty()) {
                        contPatronServicio++;
                    }
                    E2 = -1;
                    ordenarEventos();
                    NE--;
                    contServicioClientes++;
                    condInic.add(String.valueOf(NE));
                    if (contServicioClientes == FormularioEntrada.LimiteServicio || FormularioEntrada.E3==tmpEvt) {
                        condInic.add("-");
                        condInic.add("-");
                        condInic.add(LE());
                        resultadosTabla.add(condInic);
                    } else {
                        boolean flag = false;
                        if (NE != 0) {
                            flag = true;
                            double rn = rnd.nextRandom(_INT_MAX_VALUE);
                            condInic.add(String.valueOf(rn));

                            double pattern = org.system.patrones.Formulas.patronServicio(rn);

                            String formatoFormula = Formulas.formatoPatronServicio(rn, 3, 6);

                            formulaServicioClientes.add(formatoFormula);
                            patronServicioClientes.add(pattern);

                            condInic.add("E2/" + String.valueOf(round(pattern + tmpEvt, 2)));
                            E2 = round(pattern + tmpEvt, 2);
                            ordenarEventos();
                            condInic.add(LE());
                            resultadosTabla.add(condInic);
                        }

                        boolean flagEvt = false;
                        if (!findEvent("E1")) {
                            flagEvt = true;
                            if (flag) {
                                condInic = new ArrayList<>();
                                condInic.add("-");
                                condInic.add("-");
                                condInic.add("-");
                            }
                            double rn = rnd.nextRandom();
                            condInic.add(String.valueOf(rn));

                            double pattern = org.system.patrones.Formulas.patronLlegada(rn);

                            String formatoFormula = Formulas.formatoPatronLlegada(rn, 3, 6);

                            formulaLlegadaClientes.add(formatoFormula);
                            patronLlegadaClientes.add(pattern);

                            condInic.add("E1/" + String.valueOf(round(pattern + tmpEvt, 2)));
                            E1 = round(pattern + tmpEvt, 2);
                            ordenarEventos();
                            condInic.add(LE());
                            resultadosTabla.add(condInic);
                        } 
                        if (!flag && !flagEvt) {
                            ordenarEventos();
                            condInic.add("-");
                            condInic.add("-");
                            condInic.add(LE());
                            resultadosTabla.add(condInic);
                        }
                    }
                } else {
                    condInic.add(String.valueOf(NE));
                    condInic.add("-");
                    condInic.add("-");
                    E3 = -1;
                    ordenarEventos();
                    condInic.add(LE());
                    resultadosTabla.add(condInic);
                }
            }
        }
    }

    /*
        Método donde se establecen las condiciones iniciales de la corrida de simulación.
        Se verifica además si ocurre un evento en el tiempo 0, en cuyo caso se aplica el
        procedimiento definido en el método calcularResultados(). Además, si no se proporcionan
        los tiempos de los próximos eventos, se realiza el procedimiento de la nota 3, descrito
        en el método calcularResultados()
    */
    public void condicionesIniciales() {
        
        E1 = FormularioEntrada.E1;
        E2 = FormularioEntrada.E2;
        E3 = FormularioEntrada.E3;

        ordenarEventos();
        
        ArrayList<String> condInic = new ArrayList<>();

        condInic.add("0.00");

        if (E1 != -1 || E2 != -1) {

            if (eventos.size() > 0) {

                double tmpEvt = round(Double.parseDouble(eventos.get(0).split("/")[1]), 2);

                if (tmpEvt == 0) {
                    String strTmpEvt = eventos.get(0).split("/")[0];
                    condInic.add(strTmpEvt);
                    if (strTmpEvt.equalsIgnoreCase("E1")) { //Nota 1
                        E1 = -1;
                        ordenarEventos();
                        NE++;
                        contLlegadaClientes++;
                        condInic.add(String.valueOf(NE));
                        if (contLlegadaClientes == FormularioEntrada.LimiteLlegada || FormularioEntrada.E3==0) {
                            condInic.add("-");
                            condInic.add("-");
                            condInic.add(LE());
                            resultadosTabla.add(condInic);
                        } else {
                            double rn = rnd.nextRandom();
                            condInic.add(String.valueOf(rn));

                            double pattern = org.system.patrones.Formulas.patronLlegada(rn);

                            String formatoFormula = Formulas.formatoPatronLlegada(rn, 3, 6);

                            formulaLlegadaClientes.add(formatoFormula);
                            patronLlegadaClientes.add(pattern);

                            condInic.add("E1/" + String.valueOf(pattern));
                            E1 = pattern;
                            ordenarEventos();
                            condInic.add(LE());
                            resultadosTabla.add(condInic);

                            if (!findEvent("E2") && NE != 0) {
                                condInic = new ArrayList<>();
                                condInic.add("-");
                                condInic.add("-");
                                condInic.add("-");
                                rn = rnd.nextRandom(_INT_MAX_VALUE);
                                condInic.add(String.valueOf(rn));

                                pattern = org.system.patrones.Formulas.patronServicio(rn);

                                formatoFormula = Formulas.formatoPatronServicio(rn, 3, 6);

                                formulaServicioClientes.add(formatoFormula);
                                patronServicioClientes.add(pattern);

                                condInic.add("E2/" + String.valueOf(pattern));
                                E2 = pattern;
                                ordenarEventos();
                                condInic.add(LE());
                                resultadosTabla.add(condInic);
                            }
                        }
                    } else {
                        if (strTmpEvt.equalsIgnoreCase("E2")) { //Nota 2
                            E2 = -1;
                            ordenarEventos();
                            NE--;
                            contServicioClientes++;
                            condInic.add(String.valueOf(NE));
                            if (contServicioClientes == FormularioEntrada.LimiteServicio || FormularioEntrada.E3==0) {
                                condInic.add("-");
                                condInic.add("-");
                                condInic.add(LE());
                                resultadosTabla.add(condInic);
                            } else {
                                boolean flag = false;
                                if (NE != 0) {
                                    flag = true;
                                    double rn = rnd.nextRandom(_INT_MAX_VALUE);
                                    condInic.add(String.valueOf(rn));

                                    double pattern = org.system.patrones.Formulas.patronServicio(rn);

                                    String formatoFormula = Formulas.formatoPatronServicio(rn, 3, 6);

                                    formulaServicioClientes.add(formatoFormula);
                                    patronServicioClientes.add(pattern);

                                    condInic.add("E2/" + String.valueOf(pattern));
                                    E2 = pattern;
                                    ordenarEventos();
                                    condInic.add(LE());
                                    resultadosTabla.add(condInic);
                                }

                                boolean flagEvt = false;
                                if (!findEvent("E1")) {
                                    flagEvt = true;
                                    if (flag) {
                                        condInic = new ArrayList<>();
                                        condInic.add("-");
                                        condInic.add("-");
                                        condInic.add("-");
                                    }
                                    double rn = rnd.nextRandom();
                                    condInic.add(String.valueOf(rn));

                                    double pattern = org.system.patrones.Formulas.patronLlegada(rn);

                                    String formatoFormula = Formulas.formatoPatronLlegada(rn, 3, 6);

                                    formulaLlegadaClientes.add(formatoFormula);
                                    patronLlegadaClientes.add(pattern);

                                    condInic.add("E1/" + String.valueOf(pattern));
                                    E1 = pattern;
                                    ordenarEventos();
                                    condInic.add(LE());
                                    resultadosTabla.add(condInic);
                                }
             
                                if (!flag && !flagEvt) {
                                    ordenarEventos();
                                    condInic.add("-");
                                    condInic.add("-");
                                    condInic.add(LE());
                                    resultadosTabla.add(condInic);
                                }
                            }
                        } else {
                            condInic.add("E3");
                            condInic.add(String.valueOf(NE));
                            condInic.add("-");
                            condInic.add("-");
                            E3 = -1;
                            ordenarEventos();
                            condInic.add(LE());
                            resultadosTabla.add(condInic);
                        }
                    }
                } else {
                    condInic.add("-");
                    condInic.add(String.valueOf(NE));
                    condInic.add("-");
                    condInic.add("-");
                    condInic.add(LE());
                    resultadosTabla.add(condInic);
                }
            }
        } else { //Nota 3
            if (E3 == 0) {
                E3 = -1;
                ordenarEventos();
                condInic.add("E3");
                condInic.add(String.valueOf(NE));
                condInic.add("-");
                condInic.add("-");
                condInic.add("-");
                resultadosTabla.add(condInic);
                return;
            }
            condInic.add("-");
            condInic.add(String.valueOf(NE));

            double rn = rnd.nextRandom();
            condInic.add(String.valueOf(rn));

            double pattern = org.system.patrones.Formulas.patronLlegada(rn);

            String formatoFormula = Formulas.formatoPatronLlegada(rn, 3, 6);

            formulaLlegadaClientes.add(formatoFormula);
            patronLlegadaClientes.add(pattern);

            condInic.add("E1/" + String.valueOf(pattern));
            E1 = pattern;
            ordenarEventos();
            condInic.add(LE());
            resultadosTabla.add(condInic);

            if (NE != 0) {
                condInic = new ArrayList<>();
                condInic.add("-");
                condInic.add("-");
                condInic.add("-");

                rn = rnd.nextRandom(_INT_MAX_VALUE);
                condInic.add(String.valueOf(rn));

                pattern = org.system.patrones.Formulas.patronServicio(rn);

                formatoFormula = Formulas.formatoPatronServicio(rn, 3, 6);

                formulaServicioClientes.add(formatoFormula);
                patronServicioClientes.add(pattern);

                condInic.add("E2/" + String.valueOf(pattern));
                E2 = pattern;
                ordenarEventos();
                condInic.add(LE());
                resultadosTabla.add(condInic);
            }
        }
    }

    /*
        Método donde se muestran las estadísticas generadas.
    */
    public void mostrarEstadisticasFinales() {
        
        String message = "<html> <body> Contadores y sumarios:<br>";
        message += " 1) <u>Número de entradas efectuadas</u>: " + String.valueOf(contLlegadaClientes);
        message += (contLlegadaClientes == 1)? " entrada<br>":" entradas<br>";
        message += " 2) <u>Número de clientes atendidos</u>: " + String.valueOf(contServicioClientes);
        message += (contServicioClientes == 1)? " cliente atendido<br>":" clientes atendidos<br>";
        message += " 3) <u>Número máximo de clientes en cola</u>: " + (NEMaximo==0? "0" : String.valueOf(NEMaximo-1)) + "<br>";
        message += " 4) <u>Número máximo de clientes en el Sistema</u>: " + String.valueOf(NEMaximo) + "<br>";
        message += " 5) <u>Tiempo promedio entre llegadas</u>: " + formatoDecimal(tiempoPromedioLlegada) + " m <br>";
        message += " 6) <u>Tiempo promedio de servicio</u>: " + formatoDecimal(tiempoPromedioServicio) + " m <br>";
        message += " 7) <u>Tiempo de ocio del Servidor</u>: " + formatoDecimal(tiempoOcioServidor) + " m <br>";
        message += " 8) <u>Tiempo de utilización del Servidor</u>: " + formatoDecimal(tiempoUtilizacionServidor) + " m <br>";
        message += " 9) <u>Tiempo promedio de espera en cola</u>: " + formatoDecimal(tiempoPromedioEsperaCola) + " m <br>";
        message += "10) <u>Tiempo promedio de espera en el Sistema</u>: " + formatoDecimal(tiempoPromedioEsperaSistema) + " m";
        message += "</html> </body>";
        
        JOptionPane.showMessageDialog(this, message, "Resultados de la corrida", JOptionPane.INFORMATION_MESSAGE);
        
    }
    
    /*
        Métodos getter and setter del atributo estadisticasGeneradas
    */
    
    public boolean getEstadisticasGeneradas() {
        return estadisticasGeneradas;
    }

    public void setEstadisticasGeneradas(boolean estadisticasGeneradas) {
        this.estadisticasGeneradas = estadisticasGeneradas;
    }
    
    /*
        Métodos getter and setter del systemTimer
    */
    public SystemTimer getSystemTimer() {
        return systemTimer;
    }

    public void setSystemTimer(SystemTimer systemTimer) {
        this.systemTimer = systemTimer;
    }
    
    /*
     Bloque de instrucciones que cargan los componentes principales de la interfaz,
     y su respectivo temporizador, llevando el control de la hora del Sistema.
     */
    public TablaResultados() {
        initComponents();
        rnd = new Rand();
        systemTimer = null;
        NE = FormularioEntrada.NroEntidades;
        //setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("org/icons/iconos/clock.png")));
        getContentPane().setBackground(new Color(209,233,239));
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        menu_inicio = new javax.swing.JLabel();
        hora = new javax.swing.JLabel();
        fecha = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaResultadosFinales = new javax.swing.JTable();
        EstadisticasFinales = new javax.swing.JButton();
        Eventos = new javax.swing.JButton();
        PatronServicio = new javax.swing.JButton();
        volver = new javax.swing.JButton();
        EstructuraModelo = new javax.swing.JButton();
        PLlegada = new javax.swing.JLabel();
        PServicio = new javax.swing.JLabel();
        PatronLlegada = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        PatronesServicio = new javax.swing.JList();
        jScrollPane5 = new javax.swing.JScrollPane();
        PatronesLlegada = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Resultados obtenidos");
        setBackground(new java.awt.Color(209, 233, 239));
        setExtendedState(MAXIMIZED_BOTH);
        setMinimumSize(new java.awt.Dimension(1180, 690));
        setPreferredSize(new java.awt.Dimension(1210, 720));
        getContentPane().setLayout(new java.awt.FlowLayout());

        jPanel1.setBackground(new java.awt.Color(209, 233, 239));
        jPanel1.setMinimumSize(new java.awt.Dimension(1180, 680));
        jPanel1.setPreferredSize(new java.awt.Dimension(1180, 680));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menu_inicio.setFont(new java.awt.Font("Corbel", 1, 24)); // NOI18N
        menu_inicio.setForeground(new java.awt.Color(102, 102, 102));
        menu_inicio.setText("RESULTADOS FINALES");
        jPanel1.add(menu_inicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 60, 270, 40));

        hora.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        hora.setForeground(new java.awt.Color(209, 233, 239));
        hora.setText("Hora:");
        jPanel1.add(hora, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 40, 188, 35));

        fecha.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        fecha.setForeground(new java.awt.Color(209, 233, 239));
        fecha.setText("Fecha:");
        jPanel1.add(fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 20, 188, 34));

        TablaResultadosFinales.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        TablaResultadosFinales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "T.R.", "E.Q.", "N.E.", "<html> <body> R<sub>n</sub>", "E.G. ", "L.E."
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaResultadosFinales.setGridColor(new java.awt.Color(255, 255, 255));
        TablaResultadosFinales.setSelectionBackground(new java.awt.Color(50, 72, 135));
        jScrollPane1.setViewportView(TablaResultadosFinales);
        if (TablaResultadosFinales.getColumnModel().getColumnCount() > 0) {
            TablaResultadosFinales.getColumnModel().getColumn(0).setPreferredWidth(20);
            TablaResultadosFinales.getColumnModel().getColumn(1).setPreferredWidth(5);
            TablaResultadosFinales.getColumnModel().getColumn(2).setPreferredWidth(10);
            TablaResultadosFinales.getColumnModel().getColumn(3).setPreferredWidth(30);
            TablaResultadosFinales.getColumnModel().getColumn(4).setPreferredWidth(110);
            TablaResultadosFinales.getColumnModel().getColumn(5).setPreferredWidth(200);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 100, 760, 340));

        EstadisticasFinales.setBackground(new java.awt.Color(255, 255, 255));
        EstadisticasFinales.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        EstadisticasFinales.setForeground(new java.awt.Color(102, 102, 102));
        EstadisticasFinales.setText("<html> <body> Estadísticas finales </body> </html>");
        EstadisticasFinales.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        EstadisticasFinales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EstadisticasFinalesActionPerformed(evt);
            }
        });
        jPanel1.add(EstadisticasFinales, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 180, 50));

        Eventos.setBackground(new java.awt.Color(255, 255, 255));
        Eventos.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        Eventos.setForeground(new java.awt.Color(102, 102, 102));
        Eventos.setText("Eventos");
        Eventos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Eventos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EventosActionPerformed(evt);
            }
        });
        jPanel1.add(Eventos, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 180, 50));

        PatronServicio.setBackground(new java.awt.Color(255, 255, 255));
        PatronServicio.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        PatronServicio.setForeground(new java.awt.Color(102, 102, 102));
        PatronServicio.setText("<html> <body> Patrón de servicio </body> </html>");
        PatronServicio.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        PatronServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PatronServicioActionPerformed(evt);
            }
        });
        jPanel1.add(PatronServicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 180, 50));

        volver.setBackground(new java.awt.Color(26, 42, 84));
        volver.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        volver.setForeground(new java.awt.Color(255, 255, 255));
        volver.setText("Volver");
        volver.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        volver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volverActionPerformed(evt);
            }
        });
        jPanel1.add(volver, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 620, 140, 30));

        EstructuraModelo.setBackground(new java.awt.Color(255, 255, 255));
        EstructuraModelo.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        EstructuraModelo.setForeground(new java.awt.Color(102, 102, 102));
        EstructuraModelo.setText("<html> <body> Estructura<br>del modelo </body> </html>");
        EstructuraModelo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        EstructuraModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EstructuraModeloActionPerformed(evt);
            }
        });
        jPanel1.add(EstructuraModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 180, 50));

        PLlegada.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        PLlegada.setForeground(new java.awt.Color(102, 102, 102));
        PLlegada.setText("Patrón de llegada");
        jPanel1.add(PLlegada, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 450, 150, 30));

        PServicio.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        PServicio.setForeground(new java.awt.Color(102, 102, 102));
        PServicio.setText("Patrón de servicio");
        jPanel1.add(PServicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 450, 150, 30));

        PatronLlegada.setBackground(new java.awt.Color(255, 255, 255));
        PatronLlegada.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        PatronLlegada.setForeground(new java.awt.Color(102, 102, 102));
        PatronLlegada.setText("<html> <body> Patrón de llegada </body> </html>");
        PatronLlegada.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        PatronLlegada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PatronLlegadaActionPerformed(evt);
            }
        });
        jPanel1.add(PatronLlegada, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, 180, 50));

        PatronesServicio.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        PatronesServicio.setSelectionBackground(new java.awt.Color(50, 72, 135));
        jScrollPane4.setViewportView(PatronesServicio);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 480, 360, 170));

        PatronesLlegada.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        PatronesLlegada.setSelectionBackground(new java.awt.Color(50, 72, 135));
        jScrollPane5.setViewportView(PatronesLlegada);

        jPanel1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 480, 380, 170));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/icons/iconos/logopeqcorp2.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
     Esta sección de código corresponde a los eventos de la interfaz.
     Los correspondientes a botones, formularios, entre otros.
     */
    private void EstadisticasFinalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EstadisticasFinalesActionPerformed
        
        generarEstadisticas();

    }//GEN-LAST:event_EstadisticasFinalesActionPerformed
    
    private void EventosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EventosActionPerformed

        String messageEventos = "<html> <body> E<sub>1</sub>: Llegada de un Cliente al Sistema => E<sub>1</sub>/";
        if (org.system.inicio.FormularioEntrada.E1 == -1) {
            messageEventos += "?<br>";
        } else {
            messageEventos += org.system.inicio.FormularioEntrada.TPL.getText() + " min <br>";
        }
        messageEventos += "E<sub>2</sub>: Culminación del servicio a un cliente => E<sub>2</sub>/";
        if (org.system.inicio.FormularioEntrada.E2 == -1) {
            messageEventos += "?<br>";
        } else {
            messageEventos += org.system.inicio.FormularioEntrada.TPS.getText() + " min <br>";
        }
        if (org.system.inicio.FormularioEntrada.E3 != -1) {
            messageEventos += "E<sub>3</sub>: Finalización de la corrida de Simulación => E<sub>3</sub>/" + org.system.inicio.FormularioEntrada.CondicionParada.getText() + " min </body> <html>";
        }
        JOptionPane.showMessageDialog(this, messageEventos, "Eventos iniciales", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_EventosActionPerformed

    private void PatronServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PatronServicioActionPerformed
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator(',');
        DecimalFormat formato = new DecimalFormat("0.000000", simbolos);
        String formatoFormula = "<html>"
                + "<body>"
                + "Distribución Loglogística (3 parámetros) <br>"
                + "X<sub>i</sub> = &gamma + &beta <sup> &alpha</sup>&#8730(R<sub>i</sub>/(1-R<sub>i</sub>)) <br>"
                + "&gamma = " + formato.format(org.system.constants.constantes.ConstantesGlobales._Patron_Servicio_Gamma) + " <br>"
                + "&beta = " + formato.format(org.system.constants.constantes.ConstantesGlobales._Patron_Servicio_Beta) + " <br>"
                + "&alpha = " + formato.format(org.system.constants.constantes.ConstantesGlobales._Patron_Servicio_Alpha)
                + "</body>"
                + "<html>";
        JOptionPane.showMessageDialog(this, formatoFormula, "Patrón de servicio", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_PatronServicioActionPerformed

    private void volverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverActionPerformed
        this.setVisible(false);
        FormularioEntrada EntradaDatos = new FormularioEntrada();
        EntradaDatos.setVisible(true);
        if (systemTimer != null) {
            systemTimer.stopTimer();
        }
        EntradaDatos.setSystemTimer(new SystemTimer());
        EntradaDatos.getSystemTimer().createTimerFormat12Hour(EntradaDatos, "fecha", "hora");
    }//GEN-LAST:event_volverActionPerformed

    private void EstructuraModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EstructuraModeloActionPerformed
        String estructura_modelo = "<html> <body> 1) Servidor: 1<br>"
                + "2) Entidades potenciales: Infinitas<br>"
                + "3) Disciplina de cola: FIFO<br>"
                + "4) T.R.: Tiempo del Reloj<br>"
                + "5) E.Q.: Evento de Ocurre<br>"
                + "6) N.E.: Número de Entidades en el Sistema<br>"
                + "7) R<sub>n</sub>: Número pseudoaleatorio<br>"
                + "8) E.G.: Evento Generado<br>"
                + "9) L.E.: Lista de Eventos </body> </html>";
        JOptionPane.showMessageDialog(this, estructura_modelo, "Estructura del modelo", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_EstructuraModeloActionPerformed

    private void PatronLlegadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PatronLlegadaActionPerformed
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator(',');
        DecimalFormat formato = new DecimalFormat("0.000000", simbolos);
        String formatoFormula = "<html>"
                + "<body>"
                + "Distribución de Weibull (3 parámetros) <br>"
                + "X<sub>wei</sub> = &gamma + &beta(-ln(R<sub>i</sub>))<sup>1/&alpha</sup> <br>"
                + "&gamma = " + formato.format(org.system.constants.constantes.ConstantesGlobales._Patron_Llegada_Gamma) + " <br>"
                + "&beta = " + formato.format(org.system.constants.constantes.ConstantesGlobales._Patron_Llegada_Beta) + " <br>"
                + "&alpha = " + formato.format(org.system.constants.constantes.ConstantesGlobales._Patron_Llegada_Alpha)
                + "</body>"
                + "<html>";
        JOptionPane.showMessageDialog(this, formatoFormula, "Patrón de llegada", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_PatronLlegadaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TablaResultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TablaResultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TablaResultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TablaResultados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            TablaResultados ResultadosFinales = new TablaResultados();
            ResultadosFinales.setVisible(true);
            if (ResultadosFinales.getSystemTimer() != null) {
                ResultadosFinales.getSystemTimer().stopTimer();
            }
            ResultadosFinales.setSystemTimer(new SystemTimer());
            ResultadosFinales.getSystemTimer().createTimerFormat12Hour(ResultadosFinales, "fecha", "hora");
        });
    }
    
    /*
        Método donde se actualizan los componentes de la interfaz
    */
    public void refrescarComponentesInterfaz() {
        this.validate();
        this.repaint();
        this.jPanel1.validate();
        this.jPanel1.repaint();
        this.jScrollPane1.validate();
        this.jScrollPane1.repaint();
        this.jScrollPane4.validate();
        this.jScrollPane4.repaint();
        this.jScrollPane5.validate();
        this.jScrollPane5.repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton EstadisticasFinales;
    public static javax.swing.JButton EstructuraModelo;
    public static javax.swing.JButton Eventos;
    private static javax.swing.JLabel PLlegada;
    private static javax.swing.JLabel PServicio;
    public static javax.swing.JButton PatronLlegada;
    public static javax.swing.JButton PatronServicio;
    private javax.swing.JList PatronesLlegada;
    private javax.swing.JList PatronesServicio;
    public static javax.swing.JTable TablaResultadosFinales;
    public static javax.swing.JLabel fecha;
    public static javax.swing.JLabel hora;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private static javax.swing.JLabel menu_inicio;
    public static javax.swing.JButton volver;
    // End of variables declaration//GEN-END:variables

}
