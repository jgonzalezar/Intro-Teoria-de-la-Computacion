/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LectoresYProcesos;

import AutomatasFinitos.AFD;
import Herramientas.Respuesta;
import java.util.ArrayList;

/**
 *
 * @author fanat
 */
public class ProcesamientoCadenaAFD  {
    private String cadena;
    private boolean esAceptada;
    private ArrayList<String> listaEstadoSimboloDeProcesamiento;
    private AFD afd;
    private boolean procesado;

    public ProcesamientoCadenaAFD(String cadena,AFD afd) {
        this.cadena = cadena;
        this.afd = afd;
        proces();
    }
    
    

    public String getCadena() {
        if(procesado)return cadena;
        else {
            System.err.println("No se ah dado cadena");
            return null;
        }
    }

    public boolean isEsAceptada() {
        if(procesado)return esAceptada;
        else {
            System.err.println("No se ah realizado procesamiento de cadena");
            return false;
        }
    }

    public ArrayList<String> getListaEstadoSimboloDeProcesamiento() {
        if(procesado) return listaEstadoSimboloDeProcesamiento;
        else {
            System.err.println("No se ah realizado procesamiento de cadena");
            return null;
        }
    }

    public AFD getAfd() {
        return afd;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
        procesado = false;
    }

    public void setAfd(AFD afd) {
        this.afd = afd;
        procesado = false;
    }
    
    public void setAfd(String nombreDeArchivo) {
        this.afd = new AFD;
        procesado = false;
    }
    
    private void proces(){
        procesarCadena();
    }
    
    public void procesarCadena(){
        Respuesta  res = afd.prosCaden(cadena);
        listaEstadoSimboloDeProcesamiento=res.pasos;
        esAceptada = res.aceptado;
        procesado=true;
    }
    
    
}
