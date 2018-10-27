/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thread.hilos;

/*
    Clase donde se realiza una pausa en el Sistema
*/
public class SystemPause {
    
    /*
        Método donde se realiza una pausa de n milisegundos en el Sistema
    */
    public static void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {}
    }
    
}
