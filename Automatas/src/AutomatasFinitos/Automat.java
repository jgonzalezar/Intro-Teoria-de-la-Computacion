/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomatasFinitos;

import java.util.ArrayList;
import Herramientas.Transitions;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author fanat
 */
public abstract class Automat {
    /**
     * El atributo Sigma representa el alfabeto del automata.
     */
    
    protected char[] Sigma;
    /**
     * El atributo Q representa el conjunto de estados que pertenecen al automata.
     */
    protected ArrayList<String> Q;
    /**
     * El atributo q0 representa el estado inicial del automata.
     */
   
    protected Integer q0;
    /**
     * El atributo F representa el conjunto de estados de aceptación del automata.
     */
    
    protected ArrayList<Integer> F;
    /**
     * El atributo Delta representa las transiciones del automata.
     */
    protected Transitions Delta;
    /**
     *Devuelve la cadena como una lista de caracteres ,verifica si los caracteres ingresados por el usuario
     * para procesar una cadena pertenecen al alfabeto.
     * @param cadena cadena a evaluar.
     * @return Lista de caracteres que no corresponden al alfabeto. 
     */
    public ArrayList<Character> ponerCadena(String cadena){
        ArrayList<Character> asd = new ArrayList<>();
        for (int i = 0; i < cadena.length(); i++) {
            boolean d = false;
            for (char c : Sigma) {
                if(c==cadena.charAt(i)) d=true;
            }
            if(!d) asd.add(cadena.charAt(i));
        }
        
        Set<Character> hashSet = new HashSet<>(asd);
        asd.clear();
        asd.addAll(hashSet);
        return asd;
    }

    public String[] GetQ() {
        String[] s = new String[Q.size()];
        for (int i=0;i<Q.size();i++) {
            s[i]=Q.get(i);
        }
        return s;
    }
    
    public abstract void ImprimirlambdaClausura_unEstado(int estado);
    
    public abstract void ImprimirlambdaClausura_variosEstado(ArrayList<Integer> estados);
    
    public abstract boolean procesarCadenaConDetalles(String word);
    
    public abstract boolean procesarCadena(String word);
    
    public abstract int computarTodosLosProcesamientos(String cadena, String nombreArchivo);
    
    public abstract void imprimirComputaciones(String cadena, int computacion);
    
    public abstract void procesarListaCadenas(String[] listaCadenas,String nombreArchivo,boolean imprimirPantalla);
}
