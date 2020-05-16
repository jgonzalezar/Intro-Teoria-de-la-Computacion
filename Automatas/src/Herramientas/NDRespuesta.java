/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;

import java.util.ArrayList;

/**
 *
 * @author 
 */
public class NDRespuesta {
    public String cadena;
    public ArrayList<Integer> aceptado;
    public ArrayList<Integer> rechazado;
    public ArrayList<Integer> abortado;

    public NDRespuesta(String cadena) {
        this.cadena = cadena;
        aceptado = new ArrayList<>();
        rechazado = new ArrayList<>();
        abortado = new ArrayList<>();
    }

    
    public void addAceptado(Integer paso){
        aceptado.add(paso);       
    }
    
    public void addRechazado(Integer paso){
        rechazado.add(paso);       
    }
    
    public void addAbortado(Integer paso){
        abortado.add(paso);       
    }
    
    //Hacer lo mismo para rechazado y abortado
    public ArrayList<String> getAccepted(){
        ArrayList<String> accepted = new ArrayList<>();
        String s;
        for(int i=0;i<aceptado.size()/cadena.length();i++){
             s="q0";
            for(int j=0;j<cadena.length();j++){
                s += " "+cadena.substring(j,j+1)+",q"+aceptado.get(j+i*cadena.length());
            }
            accepted.add(s);
        }
        return accepted;
    }
    
    public ArrayList<String> getRejected(){
        ArrayList<String> rejected = new ArrayList<>();
        String s;
        for(int i=0;i<rechazado.size()/cadena.length();i++){
            s ="q0";
            for(int j=0;j<cadena.length();j++){
                s += " "+cadena.substring(j,j+1)+",q"+rechazado.get(j+i*cadena.length());
            }
            rejected.add(s);
        }
        return rejected;
    }
    
    public ArrayList<String> getAborted(){
        ArrayList<String> aborted = new ArrayList<>();
        String s="q0";
        int cont=0;
        for(int i=0;i<abortado.size();i++){
            if(abortado.get(i)==-1){
                aborted.add(s);
                s="q0";
                cont=0;
            }else{
                s +=" "+cadena.substring(cont,cont+1)+",q"+abortado.get(i);
                cont++;
            }
        }
        return aborted;
    }
    
    public boolean isAccepted(){
        return aceptado.size()>0;      
    }
}