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
public class trioPila {
    private final Character pila;
    private final String estado;
    private final Character alphabeto;
    private final ParPila duo;

    public trioPila(Character pila, String estado, Character alphabeto,ParPila duo) {
        this.pila = pila;
        this.estado = estado;
        this.alphabeto = alphabeto;
        this.duo=duo;
    }

    public ParPila equals(Character pila,Character alph, String esta){
        if(this.pila.equals(pila)&&this.alphabeto.equals(alph)&&this.estado.equals(esta))return duo;
        return null;
        
    }
}
