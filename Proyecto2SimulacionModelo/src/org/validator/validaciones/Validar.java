/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.validator.validaciones;

/*
 Clase donde se realizan las validaciones necesarias para la
 entrada de datos
 */
public class Validar {

    /*
     Método donde se valida la precisión de un double, validando cantidad de dígitos antes del punto decimal, y después
     del punto decimal
     */
    public static boolean validarPrecision(String cad, int c, int p) {
        int pos = 0, cant_digit = 0, cant_digit_before = 0;
        while (pos < cad.length()) {
            if (Character.isDigit(cad.charAt(pos))) {
                cant_digit_before++;
            }
            if (cant_digit_before > c) {
                return false;
            }
            if (cad.charAt(pos) == ',') {
                break;
            }
            pos++;
        }
        if (pos == cad.length()) {
            return true;
        }
        for (int i = pos; i < cad.length(); i++) {
            if (Character.isDigit(cad.charAt(i))) {
                cant_digit++;
            }
        }
        return cant_digit <= p;
    }

    /*
     Método donde se valida la precisión de un número con decimales
     */
    public static boolean validarPrecision(String cad, int p) {
        int cant_decimal = 0;
        for (int i = 0; i < cad.length(); i++) {
            if ((i == 0 || i == cad.length() - 2) && cad.charAt(i) == ',') {
                if (i == 0) {
                    return false;
                } else {
                    if (cad.charAt(i + 1) == ',') {
                        return false;
                    }
                }
            }
            if (cad.charAt(i) == ',') {
                cant_decimal++;
            }
            if (cant_decimal > 1) {
                return false;
            }

        }
        String[] elements;
        elements = cad.split(",");
        return validarPrecision(cad, elements[0].length(), p);
    }
}
