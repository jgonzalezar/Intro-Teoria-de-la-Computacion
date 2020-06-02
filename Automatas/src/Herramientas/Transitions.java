/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;

import java.util.ArrayList;

/**
 *  La clase Transition sirve para simular transiciones de  automatas tipo AFD
 *  posee un hashmap para cada simbolo del alphabeto que guarda las transiciones de cada estado a siguiente estado 
 */
public abstract interface Transitions {
    
    
    
    
    public void add(Character Alphabeto, int Estado, int... estado);
    public void add(Character Alphabeto, String Estado,String estado);
    
    /**
     * cantidad de caracteres en el alfabeto
     * @return tamaño de las transiciones
     */
    

    public int size();

    /**
     * Realiza la trancision de un estado segun el caracter recibido
     * @param Alphabeto caracter que marca el donde de la transicion
     * @param Estado estado desde donde se realiza la transicion
     * @return estado al que se debe desplazar
     */
    
    public  String cambio(Character Alphabeto, String Estado);
    public  ArrayList<Integer> getMove(int i, Character c);
    
}
