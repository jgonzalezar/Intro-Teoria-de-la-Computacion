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
    public  ArrayList<String> pasos ;

    public Respuesta() {
        aceptado = false;
        pasos = new ArrayList<>();
    }

    
    public void add(String paso){
        pasos.add(paso);
        
    }
    public String pasos(String palabra){
        String s ="";
        for (int i = 0; i < pasos.size(); i++) {
            s += "["+pasos.get(i)+", "+palabra.substring(i)+"]"+(char) 124+(char)61+(char)62;
        }
        return s;
    }
    
    public static String pasos(String palabra,ArrayList<String> pasos){
        String s ="";
        for (int i = 0; i < pasos.size(); i++) {
            s += "["+pasos.get(i)+", "+palabra.substring(i)+"]"+(char) 124+(char)61+(char)62;
        }
        return s;
    }
    
}
