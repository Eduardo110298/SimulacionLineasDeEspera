/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thread.hilos;

import org.system.medidor.progreso.BarraProgreso;
import org.system.resultados.TablaResultados;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/*
    Clase donde se crea un nuevo hilo o Thread
*/
public class HiloEstadisticasFinales extends Thread {
    
    //Atributos de la clase
    protected TablaResultados ResultadosFinales = null;
    protected BarraProgreso barra = null;
    protected final int millis = 0;
    protected final int finalMillis = 1000;
    
    /*
        Constructor de la clase
    */
    public HiloEstadisticasFinales(TablaResultados ResultadosFinales) {
        this.ResultadosFinales = ResultadosFinales;
        this.barra = new BarraProgreso("Generando estadísticas...");
    }
    
    /*
        Este método se ejecuta en paralelo con otro hilo. Para explicar bien el proceso, se definen los siguientes pasos:
        
        1) Se crea un nuevo hilo de esta clase, en la interfaz que le sede el control (TablaResultados).
        2) Dicho hilo se ejecuta en paralelo con los componentes de esta clase.
        3) Mientras se van realizando las operaciones para generar las estadísticas finales de la corrida de simulación, se
        va actualizando una barra de progreso (ProgressBar) que pertenece a un Frame. De esta manera, dicha barra
        va incrementando su porcentaje conforme se establecen tales resultados.
    
        Esto quiere decir que, mientras se crea el componente ProgressBar, el mismo con este nuevo hilo, se va actualizando
        a medida que avanza, y dichos cambios son visibles para el usuario, de manera que se aprecia el efecto deseado.
        Al finalizar su ejecución, se muestra el estado final de la barra de progreso y se cierra.
    
        Ahora bien, en cuanto a la secuencia de progreso, los porcentajes se distribuyen de la siguiente manera:
        
        1) 10% para generar el Número de Entidades más alto en el Sistema.
        2) 15% para generar el tiempo promedio entre llegada.
        3) 15% para generar el tiempo promedio de servicio.
        4) 10% para generar el tiempo de ocio del servidor.
        5) 5% para generar el tiempo total.
        6) 5% para generar el tiempo de utilización del servidor.
        7) 20% para generar el tiempo promedio de espera en cola.
        8) 20% para generar el tiempo promedio de espera en el Sistema.
    
        Finalmente, con el método increment de la clase BarraProgreso, se va incrementando el medidor de porcentaje.
    */
    @Override
    public void run() {
        barra.frame.pack();
        barra.frame.setLocationRelativeTo(null);
        barra.frame.setVisible(true);
        SystemPause.pause(millis);
        ResultadosFinales.generarNEMaximo();
        barra.increment(10);
        SystemPause.pause(millis);
        ResultadosFinales.generarTiempoPromedioLlegada();
        barra.increment(15);
        SystemPause.pause(millis);
        ResultadosFinales.generarTiempoPromedioServicio();
        barra.increment(15);
        SystemPause.pause(millis);
        ResultadosFinales.generarTiempoOcioServidor();
        barra.increment(10);
        SystemPause.pause(millis);
        ResultadosFinales.generarTiempoTotal();
        barra.increment(5);
        SystemPause.pause(millis);
        ResultadosFinales.generarTiempoUtilizacionServidor();
        barra.increment(5);
        SystemPause.pause(millis);
        ResultadosFinales.generarTiempoPromedioEsperaCola();
        barra.increment(20);
        SystemPause.pause(millis);
        ResultadosFinales.generarTiempoPromedioEsperaSistema();
        barra.increment(20);
        Border border = BorderFactory.createTitledBorder("Estadísticas generadas");
        barra.current.setBorder(border);
        SystemPause.pause(finalMillis);
        barra.frame.setVisible(false);
        ResultadosFinales.setEstadisticasGeneradas(true);
        ResultadosFinales.mostrarEstadisticasFinales();
    }
    
}
