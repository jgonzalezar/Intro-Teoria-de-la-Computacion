/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;

/**
 * Clase Tupla guarda dos Datos para un camino
 * @author fanat
 */
public class Tupla {
    /**
     * String que contiene todo el camino dado
     */
    public final String caracter;
    /**
     * entero que contiene el caso de aceptacion del camino
     */
    public final Integer estado;
    /**
     * representacion entera del caso de aceptacion
     */
    public static final int Case_Aceptado =1;
    /**
     * representacion entera del caso de rechazado
     */
    public static final int Case_Rechazado= 0;
    /**
     * representacion entera del caso de Abortado
     */
    public static final int Case_Abortado= -1;
   

    /**
     * 
     * @param caracter  camino recorrido escrito por pares
     * @param estado resultado del procesamiento de ese camino
     * @throws IndexOutOfBoundsException si el estado dado no es ninguna de las constantes de la clase
     */
    
    public Tupla(String caracter, Integer estado) {
        if(estado<Case_Abortado||estado>Case_Aceptado) throw new IndexOutOfBoundsException();
        this.caracter = caracter;
        this.estado = estado;
    }
    
    
}
