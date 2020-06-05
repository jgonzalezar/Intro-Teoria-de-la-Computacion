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
public class ParPila {
    private final Character pila;
    private final String estado;

    public ParPila(Character pila, String estado) {
        this.pila = pila;
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public Character getPila() {
        return pila;
    }
    
    
    
    
}
