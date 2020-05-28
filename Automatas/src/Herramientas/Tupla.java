/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;

/**
 *
 * @author fanat
 */
public class Tupla {
    public String caracter;
    public Integer estado;
    public static final int Case_Aceptado =1;
    public static final int Case_Rechazado= 0;
    public static final int Case_Abortado= -1;

    public Tupla(String caracter, Integer estado) {
        this.caracter = caracter;
        this.estado = estado;
    }
    
    
}
