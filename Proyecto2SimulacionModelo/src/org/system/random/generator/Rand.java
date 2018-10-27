/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.system.random.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/*
 Clase donde se generan los números aleatorios necesarios
 para efectuar la simulación
 */
public class Rand {

    //Atributos de la clase
    protected Random rnd = new Random();
    protected Map<Double, Double> randMap = new HashMap<>();
    protected final int lengthDigits = (int) Math.pow(10, org.system.constants.constantes.ConstantesGlobales._Digitos_Random);
    protected int value = 0;
    protected final double _MAX_VALUE = 0.725;

    public Rand() {

    }

    //Método donde se verifica si ya fueron generados todos los números aleatorios posibles
    public boolean mapIsFull() {

        return randMap.size() >= lengthDigits - 1;

    }

    //Método donde se verifica si el número aleatorio a generar ya se encuentra en el mapa
    public boolean exists(double value) {

        return randMap.containsValue(value);

    }

    //Método donde se genera el siguiente número aleatorio
    public double nextRandom() {

        double next = (double) (Math.round(rnd.nextDouble() * (double) lengthDigits) / (double) lengthDigits);

        if (mapIsFull()) {
            while (next == 0) {
                next = (double) (Math.round(rnd.nextDouble() * (double) lengthDigits) / (double) lengthDigits);
            }
            return next;
        }

        while (exists(next) || next == 0) {

            next = (double) (Math.round(rnd.nextDouble() * (double) lengthDigits) / (double) lengthDigits);

        }

        if (next <= _MAX_VALUE) {
            value++;
        }
        
        randMap.put(next, next);

        return next;

    }

    //Método donde se genera el siguiente número aleatorio, considerando el máximo valor permitido
    public double nextRandom(int maxValue) {

        double next = (double) (Math.round(rnd.nextDouble() * (double) lengthDigits) / (double) lengthDigits);

        if (mapIsFull() || value >= maxValue) {
            while (next == 0 || next > _MAX_VALUE) {
                next = (double) (Math.round(rnd.nextDouble() * (double) lengthDigits) / (double) lengthDigits);
            }
            return next;
        }

        while (exists(next) || next == 0 || value < maxValue) {

            if (!exists(next) && next <= _MAX_VALUE) {
                value++;
                break;
            }
            
            next = (double) (Math.round(rnd.nextDouble() * (double) lengthDigits) / (double) lengthDigits);

        }

        randMap.put(next, next);

        return next;

    }

}
