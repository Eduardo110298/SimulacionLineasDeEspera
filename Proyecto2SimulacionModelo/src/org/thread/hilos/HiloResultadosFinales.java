/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thread.hilos;

//import org.system.medidor.progreso.BarraProgreso;
import org.system.resultados.TablaResultados;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/*
    Clase donde se crea un nuevo hilo o Thread
*/
public class HiloResultadosFinales extends Thread {
    
    //Atributos de la clase
    protected TablaResultados ResultadosFinales = null;
    //protected BarraProgreso barra = null;
    protected final int millis = 0;
    protected final int finalMillis = 1000;
    
    /*
        Constructor de la clase
    */
    public HiloResultadosFinales(TablaResultados ResultadosFinales) {
        this.ResultadosFinales = ResultadosFinales;
    //    this.barra = new BarraProgreso("Simulando...");
    }
    
    /*
        Este método se ejecuta en paralelo con otro hilo. Para explicar bien el proceso, se definen los siguientes pasos:
        
        1) Se crea un nuevo hilo de esta clase, en la interfaz que le sede el control (FormularioEntrada).
        2) Dicho hilo se ejecuta en paralelo con los componentes de esta clase.
        3) Mientras se van realizando las operaciones para generar los resultados de la corrida de simulación, se
        va actualizando una barra de progreso (ProgressBar) que pertenece a un Frame. De esta manera, dicha barra
        va incrementando su porcentaje conforme se establecen tales resultados.
    
        Esto quiere decir que, mientras se crea el componente ProgressBar, el mismo con este nuevo hilo, se va actualizando
        a medida que avanza, y dichos cambios son visibles para el usuario, de manera que se aprecia el efecto deseado.
        Al finalizar su ejecución, se muestra el estado final de la barra de progreso y se cierra.
    
        Ahora bien, en cuanto a la secuencia de progreso, los porcentajes se distribuyen de la siguiente manera:
        
        1) 5% para las condiciones iniciales (la primera línea de la tabla).
        2) 25% para el resto de la tabla (eventos generados, operaciones efectuadas, números pseudoaleatorios generados).
        3) 20% para cargar los datos en la tabla de resultados.
        4) 20% para cargar los datos del patrón de llegada en la lista de resultados.
        5) 20% para cargar los datos del patrón de servicio en la lista de resultados.
        6) 10% para verificar si se generaron números pseudoaleatorios repetidos.
    
        Finalmente, con el método increment de la clase BarraProgreso, se va incrementando el medidor de porcentaje.
    */
    @Override
    public void run() {
      //  barra.frame.pack();
        //barra.frame.setLocationRelativeTo(null);
        //barra.frame.setVisible(true);
        SystemPause.pause(millis);
        ResultadosFinales.condicionesIniciales();
        //barra.increment(5);
        SystemPause.pause(millis);
        ResultadosFinales.calcularResultados();
        //barra.increment(25);
        SystemPause.pause(millis);
        ResultadosFinales.mostrarTablaResultados();
        //barra.increment(20);
        SystemPause.pause(millis);
        ResultadosFinales.mostrarPatronLlegada();
        //barra.increment(20);
        SystemPause.pause(millis);
        ResultadosFinales.mostrarPatronServicio();
        //barra.increment(20);
        SystemPause.pause(millis);
        boolean numerosRnRepetidos = ResultadosFinales.numerosPseudoaleatoriosRepetidos();
        //barra.increment(10);
        Border border = BorderFactory.createTitledBorder("Simulación completada");
        //barra.current.setBorder(border);
        ResultadosFinales.refrescarComponentesInterfaz();
        SystemPause.pause(finalMillis);
        //barra.frame.setVisible(false);
        if (numerosRnRepetidos) {
            ResultadosFinales.mostrarMensajeAdvertencia("ADVERTENCIA", "Se generaron números pseudoaleatorios repetidos");
        }
    }
    
}
