package AutomatasFinitos;

import Herramientas.CreadorAutomata;
import Herramientas.Respuesta;
import Herramientas.Transition;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase es el automata finito determinista
 * en este automata se pueden realizar procesamientos de palabras sobre si mismo, confirmando si pertenece o no al lenguaje, e incluso se puede recibir una respuesta con pasos incluidos
 * 
 * @author equipo los Javas
 * @version 1.2
 */
public class AFD {
    /**
     * El atributo Sigma representa el alfabeto del automata.
     * Esta dado por la clase String, y cada una de los caracteres de Sigma es uno de los simbolos del alfabeto.     * 
     */
    public final char[] Sigma;
    /**
     * El atributo Q representa la cantidad total de estados dentro del automata.
     */
    public final ArrayList<String> Q; 
    /**
     * El atributo q0 representa el estado inicial del autómata.
     */
    public final String q0;
    /**
     * El atributo F representa los estados aceptados del autómata. 
     */
    public final ArrayList<String> F;
    /**
     * El atributo Delta representa la función de transición del autómata.
     */
    public final Transition Delta;
    
    /**
     * Constructor, inicializa los atributos.
     * @param Sigma
     * @param Q
     * @param q0
     * @param F
     * @param Delta 
     */
    public AFD(char[] Sigma, ArrayList<String> Q, String q0, ArrayList<String> F, Transition Delta) {
        this.Sigma = Sigma;
        this.Q = Q;
        this.q0 = q0;
        this.F = F;
        this.Delta = Delta;
    }
    /**
     * Constructor. Inicializa los atributos a partir del archivo de texto.
     * @param nombreArchivo 
     */
    public AFD(String nombreArchivo) {
        CreadorAutomata.Lecto lec = CreadorAutomata.Lecto.inicio;
        ArrayList<Character> alpha = null;
        ArrayList<String> Q = null;
        String q0 = null;
        ArrayList<String> F = null;
        Transition Delta = null;
        
        try {
            Scanner sca = new Scanner(new File(nombreArchivo));
            while(sca.hasNextLine()){
                String lin = sca.nextLine();
                System.out.println(lin);
                switch(lin){
                    case "#alphabet":
                        lec = CreadorAutomata.Lecto.alfabeto;
                        alpha = new ArrayList<>();
                        break;
                    case "#states":
                        if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                        lec = CreadorAutomata.Lecto.estados;
                        Q = new ArrayList<>();
                        break;
                    case "#initial":
                        if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                        if(Q==null) throw new Error("primero debe iniciarse los estados");
                        lec = CreadorAutomata.Lecto.estadoinicial;
                        break;
                    case "#accepting":
                        if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                        if(Q==null) throw new Error("primero debe iniciarse los estados");
                        if(q0==null) throw new Error("primero debe dar el estado inicial");
                        lec = CreadorAutomata.Lecto.estadoFin;
                        F = new ArrayList<>();
                        break;
                    case "#transitions":
                        if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                        if(Q==null) throw new Error("primero debe iniciarse los estados");
                        if(q0==null) throw new Error("primero debe dar el estado inicial");
                        if(F==null) throw new Error("primero debe dar los estados finales");
                        lec = CreadorAutomata.Lecto.transicion;
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
        this.Sigma = ad;
        this.Q = Q;
        this.q0 = q0;
        this.F = F;
        this.Delta = Delta;
    }
    
    

    /**
     * Verifica que la cadena no genere conflictos al ser evaluada.
     * @param word
     * @return "Verdadero" en caso de no poseer ningún conflicto.
     */

    public boolean procesarCadena(String word){
        if(word==null||word.length()==0){            
            return F.contains(q0);
        }
        return prosCaden(word).aceptado;
    }
    /**
     * Procesa una cadena para decir si pertenece al lenguaje
     * @param word palabra a determinar
     * @return "Verdadero" en caso de ser aceptada
     * @see procesarCadena
     */
    
    
    public boolean procesarCadena(char[] word){
        return procesarCadena(Arrays.toString(word));
    }
    
    /**
     * Procesa la cadena e imprime los estados que va tomando al procesar cada símbolo.
     * @param word
     * @return 
     */
    public boolean procesarCadenaConDetalles(char[] word){
        return procesarCadenaConDetalles(Arrays.toString(word));
    }
    /**
     * Evalúa el último caracter de la cadena
     * @param word
     * @return 
     */
    public Respuesta prosCaden(String word){
        return Finish(Delta(word));
    }
    /**
     * Procesa la cadena e imprime los estados que va tomando al procesar cada símbolo.
     * @param word
     * @return 
     */
    public boolean procesarCadenaConDetalles(String word){
        Respuesta fin =prosCaden(word);
        System.out.println(fin.pasos(word)+fin.aceptado);
        return fin.aceptado;
    }
    
    public void procesarListaCadenas(String[] listaCadenas,String nombreArchivo,boolean imprimirPantalla){
        FileWriter fichero1 = null;
        PrintWriter pw1 = null;
        try
        {
            File nombre = new File(nombreArchivo);
            if(!nombre.exists())nombre.createNewFile();
            fichero1 = new FileWriter(nombre);
            pw1 = new PrintWriter(fichero1);
            for (int i = 0; i < listaCadenas.length; i++){
                Respuesta res = prosCaden(listaCadenas[i]);
                String pas =res.pasos(listaCadenas[i]);
                String res2= pas+"\t"+res.aceptado;
                pw1.println(res2);
                if(imprimirPantalla) System.out.println(res2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {          
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }

    private Respuesta Delta(String word) {
        Respuesta f = new Respuesta();
        f.add(q0);
        if(word==null||word.length()==0){   
            
            f.aceptado =F.contains(q0);
            
            return  f;
        }
        if(word.length()==1){
            return Delta(f,word.charAt(0));
        }
        Respuesta det = Delta(word.substring(0, word.length()-1));
        return Delta(det,word.charAt(word.length()-1));
    }
    
    private Respuesta Delta(Respuesta i, char u) {
        /*if(!Sigma.contains(u)){
            //System.err.println("Sigmal simbolo "+u+"no pertenece a el Alfabeto");
            int as = -u.charAt(0);
            return as;
        }*/
        if(!i.pasos.get(i.pasos.size()-1).equals("-1")){
            String tas = Delta.cambio(u,i.pasos.get(i.pasos.size()-1));
            i.add(tas);
        }
        
        return i;
    }

    private Respuesta Finish(Respuesta q) {
        if(F.contains(q.pasos.get(q.pasos.size()-1))){        
            q.aceptado=true;
            
        }
        /*if(q<-1){
            char a = (char)-q;
            System.out.println(a);
            System.err.println("Sigmal simbolo \""+a+"\" no pertenece a el Alfabeto");
        }*/
        return q;
    }
}
