/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;

import java.util.HashMap;

/**
 *  La clase TransitionMult sirve para simular transiciones multiples de  automatas tipo AFN y AFNL
 *  posee un hashmap para cada simbolo del alphabeto que guarda las transiciones de cada estado a sus posibles siguientes
 * 
 * @author 
 */
public class TransitionMult {
    HashMap<Character,HashMap<String,String[]>> transicion;
    
    /**
     * crea el hash para guardar las transiciones multiples
     */
    
    public TransitionMult() {
        transicion = new HashMap<>();
        
    }
    
    /**
     * añade la transicion para una letra del alfabeto desde un estado especifico
     * @param Estado    estado desde donde se realizara la trancision
     * @param Alphabeto letra del alfabeto que pide la transicion
     * @param estado    estados a donde se realizara la transicion
     */
    public void add(Character Alphabeto, String Estado,String... estado){
        if(transicion.get(Alphabeto)==null){
            transicion.put(Alphabeto, new HashMap<>());
        }
        transicion.get(Alphabeto).put(Estado, estado);
    }
    
    /**
    * tamaño de las trancisiones
    * @return 
    */
    
    public int size() {
        return transicion.get(0).size();
    }
    
    /**
     * realiza la busqueda de transicion de un estado segun un caracter dado
     * @param Alphabeto caracter que llama al cambio
     * @param Estado    estado desde donde se realiza la trancision
     * @return estados a donde se trancisionara
     */

    public String[] cambio(Character Alphabeto, String Estado) {
        return transicion.get(Alphabeto).get(Estado);
    }
    
}
