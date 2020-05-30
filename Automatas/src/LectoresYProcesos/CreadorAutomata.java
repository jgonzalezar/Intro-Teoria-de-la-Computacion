/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LectoresYProcesos;

import AutomatasFinitos.AFD;
import AutomatasFinitos.AFN;
import Herramientas.Transition;
import Herramientas.Tuple;
import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *  clase que guarda la conversion estatica de un archivo de automata AFD a la Clase AFD
 * @author fanat
 */
public class CreadorAutomata {
    
   /**
    * estados que ayudan a determinar la informacion actual que se esta recibiendo
    */ 
    public enum Lecto{
        
            alfabeto,estados,estadoinicial,estadoFin,transicion,inicio
    }
    /**
     * enum diseñado para escoger alguno de los tipos de los automatas
     */
    
    public enum Type{
        AFD,AFN,AFNL
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
