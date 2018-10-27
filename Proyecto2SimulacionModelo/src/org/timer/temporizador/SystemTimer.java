
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.timer.temporizador;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/*
    Clase en la cual se extrae la fecha y hora del Sistema, y se adapta en su
    correspondiente formato de 12 horas.
*/

public class SystemTimer {

    protected Timer timer = null;
    protected TimerTask timerTask = null;
    
    /*
        Método en donde se extrae la fecha del Sistema con su correspondiente formato.
    */
    public static String date() {
        Calendar cal = Calendar.getInstance();
        int dia, mes, anio;
        dia = cal.get(Calendar.DATE);
        mes = cal.get(Calendar.MONTH) + 1;
        anio = cal.get(Calendar.YEAR);
        String dd = "", mm = "";
        if (dia < 10) {
            dd = "0";
        }
        if (mes < 10) {
            mm = "0";
        }
        return "Fecha: " + dd + Integer.toString(dia) + "/" + mm + Integer.toString(mes) + "/" + Integer.toString(anio);
    }

    /*
        Método en donde se extrae la hora del Sistema con su correspondiente formato (a.m., p.m.).
    */
    public static String hour12() {
        Calendar cal = Calendar.getInstance();
        int hora, minuto, segundo;
        hora = cal.get(Calendar.HOUR_OF_DAY);
        minuto = cal.get(Calendar.MINUTE);
        segundo = cal.get(Calendar.SECOND);
        String meridiano = " a.m.", hh="", mm="", ss="";
        if (hora<10) hh="0";
        if (hora>=12) {
            hora-=12;
            if (hora<10) hh="0";
            meridiano = " p.m.";
        }
        if (minuto<10) mm="0";
        if (segundo<10) ss="0";
        if (hora==0) {
            hh="";
            hora=12;
        }
        return "Hora: " + hh + Integer.toString(hora) + ": " + mm + Integer.toString(minuto) + ": " + ss + Integer.toString(segundo) + meridiano;
    }
    
    /*
        Método donde dada una clase, y sus atributos de fecha y hora, se le asignan un timer con formato de 12 horas.
    */
    public void createTimerFormat12Hour(Object classAppendTimer, String nameDate, String nameHour) {

        timerTask = new TimerTask() {

            @Override
            public void run() {
                try {
                    JLabel textDate = (JLabel) classAppendTimer.getClass().getField(nameDate).get(classAppendTimer);
                    textDate.setText(date());
                    JLabel textHour = (JLabel) classAppendTimer.getClass().getField(nameHour).get(classAppendTimer);
                    textHour.setText(hour12());
                } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(SystemTimer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        };
        
        timer = new Timer();
        
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
        
    }
    
    /*
        Método donde se detiene el timerTask y el timer en ejecución (en caso de existir)
    */
    public void stopTimer() {
        if (timer != null && timerTask != null) {
            try {
                timerTask.cancel();
                timer.cancel();
            }
            catch (Exception e) {}
        }
    }
    
}
