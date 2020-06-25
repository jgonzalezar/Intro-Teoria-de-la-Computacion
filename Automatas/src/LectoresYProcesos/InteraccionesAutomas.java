/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LectoresYProcesos;

import AutomatasFinitos.AFD;
import AutomatasFinitos.subTipesAFD.AFDComplemento;
import AutomatasFinitos.subTipesAFD.AFDHerramientasProducto.AFDProductoD;
import AutomatasFinitos.subTipesAFD.AFDHerramientasProducto.AFDProductoDSim;
import AutomatasFinitos.subTipesAFD.AFDHerramientasProducto.AFDProductoO;
import AutomatasFinitos.subTipesAFD.AFDHerramientasProducto.AFDProductoY;
import AutomatasFinitos.subTipesAFD.AFDSimplificacion;
import AutomatasFinitos.AFN;
import AutomatasFinitos.AFNL;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * la clase InteraccionesAutomas guarda algunos metodos estaticos y tipos de enum los cuales permiten interacciones entre diferentes tipos de automata
 * @author Juan Andres Gonzalez
 */
public class InteraccionesAutomas {
    
    /**
     * convierte un AFN dado en un AFD
     * @param afn afn base
     * @return afd equivalente
     */
    public static AFD AFNtoAFD(AFN afn){
        return afn.AFNtoAFD();
    }
    
    /**
     * convierte un AFNL en un AFN
     * @param afnl AFNL base
     * @return afn equivalente
     */
    
    public static AFN AFN_LambdaToAFN(AFNL afnl){
        return afnl.AFN_LambdaToAFN();
    }
    
    /**
     * convierte de un AFNL a AFD
     * @param afnl afnl base
     * @return afd equivalente
     */
    
    public static AFD AFN_LambdaToAFD(AFNL afnl){
        return afnl.AFN_LambdaToAFD();
    }
    
    /**
     * halla el complemento de un AFD dado
     * @param afdInput AFD base 
     * @return complemento del AFD
     */
    
    public static AFD hallarComplemento(AFD afdInput){
        return new AFDComplemento(afdInput);
    }
    
    /**
     * halla el producto cartesiano en interseccion
     * @param afd1 AFD base uno
     * @param afd2 AFD base dos
     * @return AFD resultante
     */
    
    public static AFD hallarProductoCartesianoY(AFD afd1, AFD afd2){
        return new AFDProductoY(afd1, afd2);
    }
    
    /**
     * halla el producto cartesiano en union
     * @param afd1 AFD base uno
     * @param afd2 AFD base dos
     * @return AFD resultante
     */
    
    public static AFD hallarProductoCartesianoO(AFD afd1, AFD afd2){
        return new AFDProductoO(afd1, afd2);
    }
    /**
     * halla el producto cartesiano en su diferencia
     * @param afd1 AFD base uno
     * @param afd2 AFD base dos
     * @return AFD resultante
     */
    
    public static AFD hallarProductoCartesianoDiferencia(AFD afd1, AFD afd2){
        return new AFDProductoD(afd1, afd2);
    }
    
    /**
     * halla el producto cartesiano en su diferencia simetrica
     * @param afd1 AFD base uno
     * @param afd2 AFD base dos
     * @return AFD resultante
     */
    
    public static AFD hallarProductoCartesianoDiferenciaSimetrica(AFD afd1, AFD afd2){
        return new AFDProductoDSim(afd1, afd2);
    }
    
    /**
     * halla el producto cartesiano segun el comando dado
     * @param afd1 AFD base uno
     * @param afd2 AFD base dos
     * @param operacion comando a realizar
     * @return AFD resultante
     */
    
    public static AFD hallarProductoCartesiano(AFD afd1, AFD afd2, String operacion){
        switch(operacion){
            case "intersección":
                return hallarProductoCartesianoY(afd1, afd2);
            
            case "union":
                return hallarProductoCartesianoO(afd1, afd2);
            
            case "diferencia":
                return hallarProductoCartesianoDiferencia(afd1, afd2);
                
            case "diferencia simétrica":
                return hallarProductoCartesianoDiferenciaSimetrica(afd1, afd2);
            default: 
                throw new AssertionError(operacion);
        }
    }
    
    /**
     * simplifica un afd dado
     * @param afdInput AFD base
     * @return simplificacion del AFD
     */
    
    
    public static AFD simplificarAFD(AFD afdInput){
        return new AFDSimplificacion(afdInput);
    }
    
    /**
    * estados que ayudan a determinar la informacion actual que se esta recibiendo
    */ 
    public static enum Lecto{
        /**
         * estado inicial
         */
        inicio,
        /**
         * se esta leyendo el afabeto
         */
        alfabeto,
        /**
         * se esta leyendo los estados        
         */
        estados,
        /**
         * se esta leyendo el estado inicial
         */
        estadoinicial,
        /**
         * se esta leyendo los estados de aceptacion
         */
        estadoFin,
        /**
         * se estan leyendo el alfabeto de pilas
         */
        pilaAlphabeto,
        
        /**
         * se esta leyendo la trancision del automata
         */
        transicion
    }
    /**
     * enum diseñado para escoger alguno de los tipos de los automatas
     */
    
    public static enum Type{
        /**
         * automata finito determinista
         */
        AFD,
        /**
         * automata finito no determinista
         */
        AFN,
        /**
         * automata finito no determinista con transiciones lambda
         */
        AFNL,
        /**
         * automata finito no determinista a automata finito determinista
         */
        AFNtoAFD,
        /**
         * automata finito no determinista con transiciones lambda a automata finito no determinista
         */
        AFNLtoAFN,
        /**
         * automata finito no determinista con transiciones lambda a automata finito determinista
         */
        AFNLtoAFD,
        /**
         * simplificar automata finito determinista
         */
        AFDsimplfic,
        /**
         * hallar complemento de un automata finito determinista
         */
        AFDcomplement,
        /**
         * hallar el producto del automata con otro automata
         */
        AFDproducto,
        
        AFPD,
    }
    
    /**
     * checa el tipo de automata que guarda un archivo
     * @param url direccion del archivo  ser analizado
     * @return typo de automata encontrado
     */
    public static Type CheckType(String url){
        if(url.endsWith("DFA")||url.endsWith("dfa"))return Type.AFD;
        if(url.endsWith("NFA")||url.endsWith("nfa"))return Type.AFN;
        if(url.endsWith("AFPD")||url.endsWith("afpd"))return Type.AFPD;
        if(url.endsWith("NFE")||url.endsWith("NFe")||url.endsWith("nfe"))return Type.AFNL;
        return lineCheck(url);
    }
    
    private static Type lineCheck(String url) {
        try {
            Scanner sca = new Scanner(new File(url));
            String s =sca.next();
            s=s.toLowerCase();
            if(s.equals("#!dfa"))return Type.AFD;
            if(s.equals("#!nfa"))return Type.AFN;
            if(s.equals("#!fpda"))return Type.AFN;
            if(s.equals("#!nfae"))return Type.AFNL;
        } catch (FileNotFoundException ex) {
            System.err.print("El archivo dado no existe");
        } catch(Exception e){
            System.err.print("El archivo dado no contiene un automata, o no cumple con las indicaciones para identificar su tipo");
        }
        return null;
        
    }
    
}
