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
 * la clase validacion contiene guarda unos automatas y comprueba junto con su tranformacion que tan diferente son los procesamientos entre si
 * @author fanat
 */
public class ClaseValidacion {
    /**
     * metodo que valida por medio de la comparacion de cadenas aleatorias de un alfabeto sobre el AFNL y su tranformacion
     * @param sigma alfabeto a utilizar
     */
    
    public void validarAFNLambdaToAFN(Alfabeto sigma,AFN listaDeAFDs[]){
       /* AFN Afn = afnl.AFN_LambdaToAFN();
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
        System.out.println(rechazadas.toString().substring(1, rechazadas.toString().length()-1));*/
    }
    /**
     * metodo que valida por medio de la comparacion de cadenas aleatorias de un alfabeto sobre el AFN y su tranformacion
     * @param sigma alfabeto a utilizar
     */
    public void validarAFNtoAFD(Alfabeto sigma,AFN listaDeAFNs[]){
        /*AFD Afd = afn.AFNtoAFD();
        int cantidadrechazados = 0;
        ArrayList <String> rechazadas = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            String cadena = sigma.generarCadenaAleatoria((int) (Math.random()*i)%100);
            if(afn.procesarCadena(cadena) != Afd.procesarCadena(cadena)){
                cantidadrechazados++;
                rechazadas.add(cadena);
            }
        }
        System.out.println("Casos con el mismo resultado: " + (5000-cantidadrechazados));
        System.out.println("Casos con diferente resultado: " + cantidadrechazados);
        System.out.println(rechazadas.toString().substring(1, rechazadas.toString().length()-1));*/
    }
    
}
