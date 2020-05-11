/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;

import java.util.ArrayList;

/**
 *
 * @author fanat
 */
public class Respuesta {
    public boolean aceptado;
    public  ArrayList<Integer> pasos ;

    public Respuesta() {
        aceptado = false;
        pasos = new ArrayList<>();
    }

    
    public void add(int paso){
        pasos.add(paso);
        
    }
    public String pasos(){
        String s ="";
        for (int i = 0; i < pasos.size(); i++) {
            s += "q"+pasos.get(i)+" ";
        }
        return s+"\n";
    }
    
}
