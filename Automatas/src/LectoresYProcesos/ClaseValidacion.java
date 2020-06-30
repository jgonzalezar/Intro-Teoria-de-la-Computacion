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
     * @param listaDeAFDs
     */
    
    public static void validarAFNLambdaToAFN(Alfabeto sigma,AFNL listaDeAFDs[]){
        int a=0;
        for (AFNL listaDeAFD : listaDeAFDs) {
            a++;
             AFN Afn = listaDeAFD.AFN_LambdaToAFN();
            int cantidadrechazados = 0;
            ArrayList<String> rechazadas = new ArrayList<>();
            for (int i = 0; i < 5000; i++) {
                String cadena = listaDeAFD.getSigma().generarCadenaAleatoria((int) (Math.random() * i) % 100);
                if (listaDeAFD.procesarCadena(cadena) != Afn.procesarCadena(cadena)) {
                    cantidadrechazados++;
                    rechazadas.add(cadena);
                }
            }
            System.out.println("automata "+ a);
            System.out.println("Casos con el mismo resultado: " + (5000 - cantidadrechazados));
            System.out.println("Casos con diferente resultado: " + cantidadrechazados);
            System.out.println(rechazadas.toString().substring(1, rechazadas.toString().length() - 1));
        }
       
    }
    /**
     * metodo que valida por medio de la comparacion de cadenas aleatorias de un alfabeto sobre el AFN y su tranformacion
     * @param listaDeAFNs
     */
    public static void validarAFNtoAFD(AFN listaDeAFNs[]){
        int a=0;
        for (AFN listaDeAFN : listaDeAFNs) {
            a++;
            AFD Afd = listaDeAFN.AFNtoAFD();
            int cantidadrechazados = 0;
            ArrayList <String> rechazadas = new ArrayList<>();
            for (int i = 0; i < 5000; i++) {
                String cadena = listaDeAFN.getSigma().generarCadenaAleatoria((int) (Math.random()*i)%100);
                if(listaDeAFN.procesarCadena(cadena) != Afd.procesarCadena(cadena)){
                    cantidadrechazados++;
                    rechazadas.add(cadena);
                }
            }
            System.out.println("automata "+ a);
            System.out.println("Casos con el mismo resultado: " + (5000-cantidadrechazados));
            System.out.println("Casos con diferente resultado: " + cantidadrechazados);
            System.out.println(rechazadas.toString().substring(1, rechazadas.toString().length()-1));
        }
        
    }
    public static void validarAFNLtoAFD(AFNL listaDeAFNs[]){
        int a=0;
        for (AFNL listaDeAFN : listaDeAFNs) {
            a++;
            AFD Afd = listaDeAFN.AFN_LambdaToAFD();
            int cantidadrechazados = 0;
            ArrayList <String> rechazadas = new ArrayList<>();
            for (int i = 0; i < 5000; i++) {
                String cadena = listaDeAFN.getSigma().generarCadenaAleatoria((int) (Math.random()*i)%100);
                if(listaDeAFN.procesarCadena(cadena) != Afd.procesarCadena(cadena)){
                    cantidadrechazados++;
                    rechazadas.add(cadena);
                }
            }
            System.out.println("automata "+ a);
            System.out.println("Casos con el mismo resultado: " + (5000-cantidadrechazados));
            System.out.println("Casos con diferente resultado: " + cantidadrechazados);
            System.out.println(rechazadas.toString().substring(1, rechazadas.toString().length()-1));
        }
        
    }
    
}
