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

    public String getCadena() {
        return cadena;
    }

    public boolean isEsAceptada() {
        return esAceptada;
    }

    public ArrayList<String> getListaEstadoSimboloDeProcesamiento() {
        return listaEstadoSimboloDeProcesamiento;
    }

    public AFD getAfd() {
        return afd;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public void setAfd(AFD afd) {
        this.afd = afd;
    }
    
    public void procesarCadena(){
        Respuesta  res = afd.prosCaden(cadena);
        listaEstadoSimboloDeProcesamiento=res.pasos;
        
        
    }
    
    
}
