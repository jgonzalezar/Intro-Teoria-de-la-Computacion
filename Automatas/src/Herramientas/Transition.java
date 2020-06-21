/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *  La clase Transition sirve para simular transiciones de  automatas tipo AFD
 *  posee un hashmap para cada simbolo del alphabeto que guarda las transiciones de cada estado a siguiente estado 
 */
public class Transition implements Transitions{
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
    
    @Override
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
    

    @Override
    public int size() {
        return transicion.size();
    }

    /**
     * Realiza la trancision de un estado segun el caracter recibido
     * @param Alphabeto caracter que marca el donde de la transicion
     * @param Estado estado desde donde se realiza la transicion
     * @return estado al que se debe desplazar
     */
    
    @Override
    public String cambio(Character Alphabeto, String Estado) {
        return transicion.get(Alphabeto).get(Estado);
    }

    @Override
    public void add(Character Alphabeto, int Estado, int... estado) {
    }

    @Override
    public ArrayList<Integer> getMove(int i, Character c) {
        return null;
    }

    @Override
    public void add(Integer initialState, String symbol, Integer finalState) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Tuple> get(int state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void add(Character Alphabeto, String Inest, Character InPila, String estadoSig, Character ToPila) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ParPila cambio(Character Alphabeto, String Inest, Character InPila) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
    }

    @Override
    public String toString() {
        String transiti = "#transitions\n";
        for (Map.Entry<Character, HashMap<String, String>> entry : transicion.entrySet()) {
            Character key = entry.getKey();
            HashMap<String, String> value = entry.getValue();
            for (Map.Entry<String, String> entry1 : value.entrySet()) {
                String key1 = entry1.getKey();
                String value1 = entry1.getValue();
                transiti+=key1+":"+key+">"+value1+"\n";
                
            }
            
        }
        return transiti;
    }

    @Override
    public void remove(String State) {
        for (Character i : transicion.keySet()) {
            transicion.get(i).remove(State);
        }
    }
    
}
