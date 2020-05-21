/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;

import AutomatasFinitos.AFD;
import AutomatasFinitos.AFN;
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
    enum Lecto{
        
            alfabeto,estados,estadoinicial,estadoFin,transicion,inicio
    }
    
    /**
     * funcion estatica que crea un AFD usando la informacion de un archivo indicado
     * @param afd direccion del archivo a usar para el AFD
     * @return un AFD con la informacion del archivo dado
     * @throws Error por indicar datos en el onder incorrecto dado que no se inicio un dato que se requiere o si algun estado dado no pertenece a la lista de estados o si algun caracter no pertenece al alfabeto
     */
    public static AFD leerAFD(String afd){
        Lecto lec = Lecto.inicio;
        ArrayList<Character> alpha = null;
        ArrayList<String> Q = null;
        String q0 = null;
        ArrayList<String> F = null;
        Transition Delta = null;
        
        try {
            Scanner sca = new Scanner(new File(afd));
            while(sca.hasNextLine()){
                String lin = sca.nextLine();
                System.out.println(lin);
                switch(lin){
                    case "#alphabet":
                        lec = Lecto.alfabeto;
                        alpha = new ArrayList<>();
                        break;
                    case "#states":
                        if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                        lec = Lecto.estados;
                        Q = new ArrayList<>();
                        break;
                    case "#initial":
                        if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                        if(Q==null) throw new Error("primero debe iniciarse los estados");
                        lec = Lecto.estadoinicial;
                        break;
                    case "#accepting":
                        if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                        if(Q==null) throw new Error("primero debe iniciarse los estados");
                        if(q0==null) throw new Error("primero debe dar el estado inicial");
                        lec = Lecto.estadoFin;
                        F = new ArrayList<>();
                        break;
                    case "#transitions":
                        if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                        if(Q==null) throw new Error("primero debe iniciarse los estados");
                        if(q0==null) throw new Error("primero debe dar el estado inicial");
                        if(F==null) throw new Error("primero debe dar los estados finales");
                        lec = Lecto.transicion;
                        Delta = new Transition();
                        break;
                    default:
                        switch(lec){
                            case alfabeto:
                                if(lin.length()<2){
                                    alpha.add(lin.charAt(0));
                                }else{
                                    int a = lin.charAt(0);
                                    int b = lin.charAt(2);
                                    int c = b-a;
                                    for (int i = 0; i <=c; i++) {
                                        char d = (char) (a+i);
                                        alpha.add(d);
                                        
                                    }
                                }
                                break;
                            case estados:
                                Q.add(lin);
                                break;
                            case estadoinicial:
                                if(!Q.contains(lin))throw new Error("el estado debe pertenecer a los estados del automata");
                                q0=lin;
                                break;
                            case estadoFin:
                                if(!Q.contains(lin))throw new Error("el estado debe pertenecer a los estados del automata");
                                F.add(lin);
                                break;
                            case transicion:
                                
                                String[] origin = lin.split(":");
                                String estado1 = origin[0];
                                if(!Q.contains(estado1))throw new Error("el estado del que se parte debe pertenecer a los estados del automata");
                                String[] origin2 = origin[1].split(">");
                                String alpfa = origin2[0];
                                if(!alpha.contains(alpfa.charAt(0)))throw new Error("el caracter de activacion debe pertenecer al alfabeto");
                                String estado2 = origin2[1];
                                if(!Q.contains(estado2))throw new Error("el estado de llegada debe pertenecer a los estados del automata");
                                Delta.add(alpfa.charAt(0), estado1, estado2);
                                break;
                            default:
                                break;
                        }

                }
                
                
                if(lin.equals("#alpabet")){
                    
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreadorAutomata.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        char[] ad =new char[alpha.size()];
        for (int i = 0; i < alpha.size(); i++) {
            ad[i]=alpha.get(i);
        }
        return new AFD(ad, Q, q0, F, Delta);
    }
    
    /**
     * funcion estatica que crea un AFN usando la informacion de un archivo indicado
     * @param afn direccion del archivo a usar para el AFN
     * @return un AFD con la informacion del archivo dado
     * @throws Error por indicar datos en el onder incorrecto dado que no se inicio un dato que se requiere o si algun estado dado no pertenece a la lista de estados o si algun caracter no pertenece al alfabeto
     */
    public static AFN leerAFN(String afn){
        Lecto lec = Lecto.inicio;
        ArrayList<Character> alpha = null;
        ArrayList<String> Q = null;
        String q0 = null;
        ArrayList<String> F = null;
        ArrayList<Tuple> Delta = null;
        
        try {
            Scanner sca = new Scanner(new File(afn));
            while(sca.hasNextLine()){
                String lin = sca.nextLine();
                System.out.println(lin);
                switch(lin){
                    case "#alphabet":
                        lec = Lecto.alfabeto;
                        alpha = new ArrayList<>();
                        break;
                    case "#states":
                        if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                        lec = Lecto.estados;
                        Q = new ArrayList<>();
                        break;
                    case "#initial":
                        if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                        if(Q==null) throw new Error("primero debe iniciarse los estados");
                        lec = Lecto.estadoinicial;
                        break;
                    case "#accepting":
                        if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                        if(Q==null) throw new Error("primero debe iniciarse los estados");
                        if(q0==null) throw new Error("primero debe dar el estado inicial");
                        lec = Lecto.estadoFin;
                        F = new ArrayList<>();
                        break;
                    case "#transitions":
                        if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                        if(Q==null) throw new Error("primero debe iniciarse los estados");
                        if(q0==null) throw new Error("primero debe dar el estado inicial");
                        if(F==null) throw new Error("primero debe dar los estados finales");
                        lec = Lecto.transicion;
                        Delta = new ArrayList<>();
                        break;
                    default:
                        switch(lec){
                            case alfabeto:
                                if(lin.length()<2){
                                    alpha.add(lin.charAt(0));
                                }else{
                                    int a = lin.charAt(0);
                                    int b = lin.charAt(2);
                                    int c = b-a;
                                    for (int i = 0; i <=c; i++) {
                                        char d = (char) (a+i);
                                        alpha.add(d);
                                        
                                    }
                                }
                                break;
                            case estados:
                                Q.add(lin);
                                break;
                            case estadoinicial:
                                if(!Q.contains(lin))throw new Error("el estado debe pertenecer a los estados del automata");
                                q0=lin;
                                break;
                            case estadoFin:
                                if(!Q.contains(lin))throw new Error("el estado debe pertenecer a los estados del automata");
                                F.add(lin);
                                break;
                            case transicion:
                                
                                String[] origin = lin.split(":");
                                String estado1 = origin[0];
                                if(!Q.contains(estado1))throw new Error("el estado del que se parte debe pertenecer a los estados del automata");
                                String[] origin2 = origin[1].split(">");
                                String alpfa = origin2[0];
                                if(!alpha.contains(alpfa.charAt(0)))throw new Error("el caracter de activacion debe pertenecer al alfabeto");
                                String[] origin3 = origin2[1].split(",");
                                for(int i=0;i<origin3.length;i++){
                                    String estado2 = origin3[i];
                                    if(!Q.contains(estado2))throw new Error("el estado de llegada debe pertenecer a los estados del automata");
                                    Delta.add(new Tuple(alpfa.substring(0), parseInt(estado1), parseInt(estado2)));
                                }
                                break;
                            default:
                                break;
                        }

                }
                
                
                if(lin.equals("#alpabet")){
                    
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreadorAutomata.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        char[] ad =new char[alpha.size()];
        for (int i = 0; i < alpha.size(); i++) {
            ad[i]=alpha.get(i);
        }
        return new AFN(ad, Q, q0, F, Delta);
    }
    
}
