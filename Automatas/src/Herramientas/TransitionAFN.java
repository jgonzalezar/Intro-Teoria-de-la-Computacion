/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;

import java.util.ArrayList;
import java.util.Collections;

/**
 * La clase TransitionAFN sirve para simular transiciones multiples de automatas
 * tipo AFN y AFNL posee un hashmap para cada simbolo del alphabeto que guarda
 * las transiciones de cada estado a sus posibles siguientes
 *
 * @author
 */
public class TransitionAFN implements Transitions {

    ArrayList<ArrayList<Tuple>> transicion;
    ArrayList<String> transformacion;

    /**
     * crea el array para guardar las transiciones multiples
     *
     * @param Q
     */
    public TransitionAFN(int Q) {
        this.transicion = new ArrayList<>();
        this.transformacion = new ArrayList<>();
        while (this.transicion.size() < Q) {
            this.transicion.add(new ArrayList<>());
        }
    }

    /**
     * añade la transicion para una letra del alfabeto desde un estado
     * especifico
     *
     * @param Estado estado desde donde se realizara la trancision
     * @param Alphabeto letra del alfabeto que pide la transicion
     * @param estado estados a donde se realizara la transicion
     */
    /**
     * tamaño de las trancisiones
     *
     * @return
     */
    @Override
    public int size() {
        return transicion.size();
    }

    /**
     * realiza la busqueda de transicion de un estado segun un caracter dado
     *
     * @param Alphabeto caracter que llama al cambio
     * @param Estado estado desde donde se realiza la trancision
     * @return estados a donde se trancisionara
     */
    @Override
    public String cambio(Character Alphabeto, String Estado) {
        return null;
    }
    
    @Override
    public void add(Character Alphabeto, int Estado, int... estado) {
        for (int i = 0; i < estado.length; i++) {
            this.add(Estado, Alphabeto.toString(), estado[i]);
        }
    }
    
    @Override
    public void add(Character Alphabeto, String Estado, String estado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public ArrayList<Integer> getMove(int i, Character c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void add(Integer initialState, String symbol, Integer finalState) {
        this.transicion.get(initialState).add(new Tuple(symbol, finalState));
    }
    
    @Override
    public String toString() {
        return "";
    }
    
    @Override
    public ArrayList<Tuple> get(int state) {
        return transicion.get(state);
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
    public void remove(String get) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
