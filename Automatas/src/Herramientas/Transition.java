/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;

import Herramientas.Tuple;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *  clase 
 * @author fanat
 */
public class Transition {
    HashMap<String,HashMap<String,String>> transicion;

    public Transition() {
        transicion = new HashMap<>();
    }
    
    /**
     *
     * @param Estado
     * @param Alphabeto
     * @param estado
     */
    public void add(String Alphabeto, String Estado,String estado){
        if(transicion.get(Alphabeto)==null){
            transicion.put(Alphabeto, new HashMap<>());
        }
        transicion.get(Alphabeto).put(Estado, estado);
    }
    

    public int size() {
        return transicion.size();
    }

    public String cambio(String Alphabeto, String Estado) {
        return transicion.get(Alphabeto).get(Estado);
    }
    
}
