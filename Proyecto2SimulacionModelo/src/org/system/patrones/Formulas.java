/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.system.patrones;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/*
 Clase donde se definen las fórmulas para el patrón de llegada
 y el patrón de servicio
 */
public class Formulas {

    //Método donde se define la fórmula para el patrón de llegada
    public static double patronLlegada(double rn) {

        final int lengthDigits = (int) Math.pow(10, org.system.constants.constantes.ConstantesGlobales._Digitos_Resultados);
        double value = org.system.constants.constantes.ConstantesGlobales._Patron_Llegada_Gamma + org.system.constants.constantes.ConstantesGlobales._Patron_Llegada_Beta
                * (Math.pow(-Math.log(rn), 1.0 / org.system.constants.constantes.ConstantesGlobales._Patron_Llegada_Alpha));

        double pattern = (double) (Math.round(value * (double) lengthDigits) / (double) lengthDigits);

        return pattern;

    }

    //Método donde se define la fórmula para el patrón de servicio
    public static double patronServicio(double rn) {

        final int lengthDigits = (int) Math.pow(10, org.system.constants.constantes.ConstantesGlobales._Digitos_Resultados);
        double value = org.system.constants.constantes.ConstantesGlobales._Patron_Servicio_Gamma + org.system.constants.constantes.ConstantesGlobales._Patron_Servicio_Beta
                * (Math.pow(rn / (double) (1.0 - rn), 1.0 / org.system.constants.constantes.ConstantesGlobales._Patron_Servicio_Alpha));

        double pattern = (double) (Math.round(value * (double) lengthDigits) / (double) lengthDigits);

        return pattern;

    }

    //Método donde se obtiene la fórmula del patrón de llegada en su respectivo formato
    public static String formatoPatronLlegada(double rn, int digitsRn, int digitsFormula) {
        String formulaRn = "0.0", formulaDigits = "0.0";
        for (int i = 1; i < digitsRn; i++) {
            formulaRn += "0";
        }
        for (int i = 1; i < digitsFormula; i++) {
            formulaDigits += "0";
        }
        if (digitsRn == 0) {
            formulaRn = "0";
        }
        if (digitsFormula == 0) {
            formulaDigits = "0";
        }
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator(',');
        DecimalFormat formato = new DecimalFormat(formulaDigits, simbolos);
        DecimalFormat formatoRn = new DecimalFormat(formulaRn, simbolos);
        String pattern = "<html>"
                + "<body>"
                + formato.format(org.system.constants.constantes.ConstantesGlobales._Patron_Llegada_Gamma) + " + "
                + formato.format(org.system.constants.constantes.ConstantesGlobales._Patron_Llegada_Beta)
                + "(-ln(" + formatoRn.format(rn) + "))<sup>1/" + formato.format(org.system.constants.constantes.ConstantesGlobales._Patron_Llegada_Alpha) + "</sup>"
                + "</body>"
                + "<html>";
        return pattern;
    }

    //Método donde se obtiene la fórmula del patrón de llegada en su respectivo formato
    public static String formatoPatronServicio(double rn, int digitsRn, int digitsFormula) {
        String formulaRn = "0.0", formulaDigits = "0.0";
        for (int i = 1; i < digitsRn; i++) {
            formulaRn += "0";
        }
        for (int i = 1; i < digitsFormula; i++) {
            formulaDigits += "0";
        }
        if (digitsRn == 0) {
            formulaRn = "0";
        }
        if (digitsFormula == 0) {
            formulaDigits = "0";
        }
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator(',');
        DecimalFormat formato = new DecimalFormat(formulaDigits, simbolos);
        DecimalFormat formatoRn = new DecimalFormat(formulaRn, simbolos);
        String pattern = "<html>"
                + "<body>"
                + formato.format(org.system.constants.constantes.ConstantesGlobales._Patron_Servicio_Gamma) + " + "
                + formato.format(org.system.constants.constantes.ConstantesGlobales._Patron_Servicio_Beta)
                + "<sup> " + formato.format(org.system.constants.constantes.ConstantesGlobales._Patron_Servicio_Alpha) + " </sup>&#8730("
                + formatoRn.format(rn) + "/ (1-" + formatoRn.format(rn) + "))"
                + "</body>"
                + "<html>";
        return pattern;
    }

}
