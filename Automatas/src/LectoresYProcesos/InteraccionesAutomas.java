/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LectoresYProcesos;

import AutomatasFinitos.AFD;
import AutomatasFinitos.AFDComplemento;
import AutomatasFinitos.AFDProductoD;
import AutomatasFinitos.AFDProductoDSim;
import AutomatasFinitos.AFDProductoO;
import AutomatasFinitos.AFDProductoY;
import AutomatasFinitos.AFDSimplificacion;
import AutomatasFinitos.AFN;
import AutomatasFinitos.AFNL;
import AutomatasFinitos.AFNL_AFN;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Juan Andres Gonzalez
 */
public class InteraccionesAutomas {
    public static AFD AFNtoAFD(AFN afn){
        return afn.AFNtoAFD();
    }
    
    public static AFN AFN_LambdaToAFN(AFNL afnl){
        return new AFNL_AFN(afnl);
    }
    
    public static AFD AFN_LambdaToAFD(AFNL afnl){
        return afnl.AFN_LambdaToAFD();
    }
    
    public static AFD hallarComplemento(AFD afdInput){
        return new AFDComplemento(afdInput);
    }
    
    public static AFD hallarProductoCartesianoY(AFD afd1, AFD afd2){
        return new AFDProductoY(afd1, afd2);
    }
    
    public static AFD hallarProductoCartesianoO(AFD afd1, AFD afd2){
        return new AFDProductoO(afd1, afd2);
    }
    
    public static AFD hallarProductoCartesianoDiferencia(AFD afd1, AFD afd2){
        return new AFDProductoD(afd1, afd2);
    }
    
    public static AFD hallarProductoCartesianoDiferenciaSimetrica(AFD afd1, AFD afd2){
        return new AFDProductoDSim(afd1, afd2);
    }
    
    public static AFD hallarProductoCartesiano(AFD afd1, AFD afd2, String operacion){
        switch(operacion){
            case "interseccion":
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
         * automatafinito no determinista con transiciones lambda
         */
        AFNL
    }
    
    /**
     * checa el tipo de automata que guarda un archivo
     * @param url direccion del archivo  ser analizado
     * @return typo de automata encontrado
     */
    public static Type CheckType(String url){
        if(url.endsWith("DFA")||url.endsWith("dfa"))return Type.AFD;
        if(url.endsWith("NFA")||url.endsWith("nfa"))return Type.AFN;
        if(url.endsWith("NFE")||url.endsWith("NFe")||url.endsWith("nfe"))return Type.AFNL;
        return lineCheck(url);
    }
    
    private static Type lineCheck(String url) {
        try {
            Scanner sca = new Scanner(new File(url));
            String s =sca.next();
            if(s.equals("#!dfa"))return Type.AFD;
            if(s.equals("#!nfa"))return Type.AFN;
            if(s.equals("#!nfae"))return Type.AFNL;
        } catch (FileNotFoundException ex) {
            System.err.print("El archivo dado no existe");
        } catch(Exception e){
            System.err.print("El archivo dado no contiene un automata, o no cumple con las indicaciones para identificar su tipo");
        }
        return null;
        
    }
    
}
