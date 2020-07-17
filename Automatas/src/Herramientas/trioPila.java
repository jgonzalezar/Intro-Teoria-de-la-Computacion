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
    private final ParPila duo;

    public trioPila(Character pila, ParPila duo) {
        this.pila = pila;
        this.duo=duo;
    }

    public ParPila equals(Character pila){        
        if(this.pila=='$')return duo;
        if(this.pila.equals(pila))return duo;
        return null;
        
    }
}
