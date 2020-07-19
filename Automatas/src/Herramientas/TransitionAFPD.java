/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *  La clase Transition sirve para simular transiciones de  automatas tipo AFD
 *  posee un hashmap para cada simbolo del alphabeto que guarda las transiciones de cada estado a siguiente estado 
 */
public class TransitionAFPD implements Transitions{
    HashMap<String,HashMap<Character,trioPila>> transicion;

    /**
     * crea el hash para guardar las transiciones 
     */
    
    public TransitionAFPD() {
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
        return null;
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
    public void add(Character Alphabeto, String Inest, Character InPila, String estadoSig, String ToPila) {
        if(transicion.get(Inest)==null){
            transicion.put(Inest, new HashMap<>());
        }
        
        transicion.get(Inest).put(Alphabeto, new trioPila(InPila, new ParPila(ToPila, estadoSig)));
    }

    @Override
    public ParPila cambio(Character Alphabeto, String Inest, Character InPila) {
        ParPila uno = transicion.get(Inest).get(Alphabeto).equals(InPila);
        return uno;
    }
    
    @Override
    public trioPila cambios(Character Alphabeto, String Inest, Character InPila) {
        ParPila uno = transicion.get(Inest).get(Alphabeto).equals(InPila);
        return transicion.get(Inest).get(Alphabeto);
    }

    @Override
    public void remove(String get) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public void add(Character Alphabeto, String Inest, Character InPila, String estadoSig, Character ToPila) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
    
    
}
