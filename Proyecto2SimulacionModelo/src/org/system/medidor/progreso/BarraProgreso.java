/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.system.medidor.progreso;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

/*
    En esta clase se instancia la barra de progreso para generar
    los resultados finales de la corrida de Simulación.
*/
public class BarraProgreso {
    
    //Atributos de la clase
    public JProgressBar current;
    public JFrame frame;
    protected int porc = 0;
    
    /*
        Método constructor de la clase.
    */
    public BarraProgreso(String title) {

        frame = new JFrame("Medidor de progreso");
        frame.setType(Window.Type.UTILITY);
        Container content = frame.getContentPane();
        current = new JProgressBar(0, 100);
        current.setValue(porc);
        current.setStringPainted(true);
        Border border = BorderFactory.createTitledBorder(title);
        current.setBorder(border);
        content.add(current, BorderLayout.CENTER);
        frame.setSize(300, 100);
        
    }
    
    /*
        Método donde se incrementa la barra de porcentaje, y se actualizan los componentes de la clase.
    */
    public void increment(int porc) {
        this.porc += porc;
        current.setValue(this.porc);
        frame.validate();
        frame.repaint();
        current.validate();
        current.repaint();
    }
    
}
