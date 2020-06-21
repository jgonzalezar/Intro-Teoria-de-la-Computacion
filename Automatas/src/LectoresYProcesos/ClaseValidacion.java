/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LectoresYProcesos;

import AutomatasFinitos.AFD;
import AutomatasFinitos.AFN;
import AutomatasFinitos.AFNL;
import AutomatasFinitos.Alfabeto;
import java.util.ArrayList;

/**
 *
 * @author fanat
 */
public class ClaseValidacion {
    private AFNL afnl;
    private AFN afn;

    public ClaseValidacion(AFNL afnl, AFN afn) {
        this.afnl = afnl;
        this.afn = afn;
    }
    
    public void validarAFNLambdaToAFN(Alfabeto sigma){
        AFN Afn = afnl.AFN_LambdaToAFN();
        int cantidadrechazados = 0;
        ArrayList <String> rechazadas = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            String cadena = sigma.generarCadenaAleatoria((int) (Math.random()*i)%100);
            if(afnl.procesarCadena(cadena) != Afn.procesarCadena(cadena)){
                cantidadrechazados++;
                rechazadas.add(cadena);
            }
        }
        System.out.println("Casos con el mismo resultado: " + (5000-cantidadrechazados));
        System.out.println("Casos con diferente resultado: " + cantidadrechazados);
        System.out.println(rechazadas.toString().substring(1, rechazadas.toString().length()-1));
    }
    
    public void validarAFNtoAFD(Alfabeto sigma){
        AFD Afd = afn.AFNtoAFD();
        int cantidadrechazados = 0;
        ArrayList <String> rechazadas = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            String cadena = sigma.generarCadenaAleatoria((int) (Math.random()*i)%100);
            if(afnl.procesarCadena(cadena) != Afd.procesarCadena(cadena)){
                cantidadrechazados++;
                rechazadas.add(cadena);
            }
        }
        System.out.println("Casos con el mismo resultado: " + (5000-cantidadrechazados));
        System.out.println("Casos con diferente resultado: " + cantidadrechazados);
        System.out.println(rechazadas.toString().substring(1, rechazadas.toString().length()-1));
    }
    
}
