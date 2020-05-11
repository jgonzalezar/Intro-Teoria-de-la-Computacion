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
 *
 * @author fanat
 */
public class Transition {
    HashMap<String,ArrayList<Integer>> transicion;

    public Transition() {
        transicion = new HashMap<>();
    }
    
    public void Put(String key, ArrayList<Integer> list){
        transicion.put(key, list);
    }
    

    public int size() {
        return transicion.size();
    }

    /*ArrayList<Tuple> get(int state) {
        
    }*/

    public int cambio(int i, String u) {
        return transicion.get(u).get(i);
    }
    
}
