/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;

import java.util.HashMap;

/**
 *  La clase Transition sirve para simular transiciones de  automatas tipo AFD
 *  posee un hashmap para cada simbolo del alphabeto que guarda las transiciones de cada estado a siguiente estado 
 */
public class Transition {
    HashMap<Character,HashMap<String,String>> transicion;

    /**
     * crea el hash para guardar las transiciones 
     */
    
    public Transition() {
        transicion = new HashMap<>();
    }
    
    /**
     *  añade la trancision para determinado estado
     * @param Estado Estado actual
     * @param Alphabeto caracter que genera la transicion
     * @param estado estado al que se realizara la transicion
     */
    
    public void add(Character Alphabeto, String Estado,String estado){
        if(transicion.get(Alphabeto)==null){
            transicion.put(Alphabeto, new HashMap<>());
        }
        transicion.get(Alphabeto).put(Estado, estado);
    }
    
    /**
     * cantidad de caracteres en el alfabeto
     * @return tamaño de las transiciones
     */
    

    public int size() {
        return transicion.size();
    }

    /**
     * Realiza la trancision de un estado segun el caracter recibido
     * @param Alphabeto caracter que marca el donde de la transicion
     * @param Estado estado desde donde se realiza la transicion
     * @return estado al que se debe desplazar
     */
    
    public String cambio(Character Alphabeto, String Estado) {
        return transicion.get(Alphabeto).get(Estado);
    }
    
}
