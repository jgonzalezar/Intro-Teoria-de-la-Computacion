package AutomatasFinitos;

import Herramientas.CreadorAutomata;
import Herramientas.NDRespuesta;
import Herramientas.Transition;
import java.util.ArrayList;
import java.util.Arrays;
import Herramientas.Tuple;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase es el automata finito no determinista
 * en este automata se puede realizar el procesamiento de cadenas sobre el automata ingresado, para saber si esta pertenece o no al lenguaje
 * mientras se muestran los pasos del procesamiento, si así se desea
 * @author jdacostabe
 */

public class AFN {
    
     /**
     * El atributo Sigma representa el alfabeto del automata.
     */
    public final char[] sigma; 
     /**
     * El atributo Q representa el conjunto de estados que pertenecen al automata.
     */
    public final ArrayList<String> Q;
     /**
     * El atributo q0 representa el estado inicial del automata.
     */
    public final int q0;
     /**
     * El atributo F representa el conjunto de estados de aceptación del automata.
     */
    public final ArrayList<Integer> F;
     /**
     * El atributo Delta representa las transiciones del automata.
     */
    public final ArrayList<ArrayList<Tuple>> Delta;
    
    private int cont;
    private String cadena;
    private Integer[] print;
    private NDRespuesta respuesta;

    
    /**
     * Constructor 
     * @param sigma Alfabeto
     * @param Q Conjunto de estados
     * @param q0 Estado inicial
     * @param F Estados de aceptación
     * @param Delta Transiciones
     */
    public AFN(char[] sigma, ArrayList<String> Q, String q0, ArrayList<String> F, ArrayList<Tuple> Delta){
        this.sigma = sigma;
        this.Q = Q;
        this.q0 = parseInt(q0);
        this.F = new ArrayList<>();
        for(int i=0;i<F.size();i++)
            this.F.add(parseInt(F.get(i)));
        
        this.Delta = new ArrayList<>();
        while(this.Delta.size()<=Q.size()) {
            this.Delta.add(new ArrayList<>());
        }
        for(int i=0;i<Delta.size();i++) {
            if(Delta.get(i).getFinalState()>Q.size() || Delta.get(i).getInitialState()>Q.size()){
                System.out.println("El número de estados ingresados no concuerda");
                return;
            }
            this.Delta.get(Delta.get(i).getInitialState()).add(new Tuple(Delta.get(i).getSymbol(),Delta.get(i).getFinalState()));
        }
        this.cont=0;
    }

    public AFN(String nombreArchivo) {
        CreadorAutomata.Lecto lec = CreadorAutomata.Lecto.inicio;
        ArrayList<Character> alpha = null;
        ArrayList<String> Q = null;
        String q0 = null;
        ArrayList<String> F = null;
        ArrayList<Tuple> Delta = null;
        
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
        this.sigma = ad;
        this.Q = Q;
        this.q0 = parseInt(q0);
        this.F = new ArrayList<>();
        for(int i=0;i<F.size();i++)
            this.F.add(parseInt(F.get(i)));
        
        this.Delta = new ArrayList<>();
        while(this.Delta.size()<=Q.size()) {
            this.Delta.add(new ArrayList<>());
        }
        for(int i=0;i<Delta.size();i++) {
            if(Delta.get(i).getFinalState()>Q.size() || Delta.get(i).getInitialState()>Q.size()){
                System.out.println("El número de estados ingresados no concuerda");
                return;
            }
            this.Delta.get(Delta.get(i).getInitialState()).add(new Tuple(Delta.get(i).getSymbol(),Delta.get(i).getFinalState()));
        }
        this.cont=0;
    }
    
    
    /**
     * Función que procesa una cadena en el automata
     * @param cadena Cadena a procesar
     * @return boolean - True si la cadena ingresada es aceptada, false de otra forma.
     */
    public boolean procesarCadena(String cadena){
        if(cadena.length()==0) {
            if(this.F.contains(q0)) {
		return true;
            }
            return false;
        }
        this.cadena=cadena;
        this.print = new Integer[cadena.length()];
        respuesta = new NDRespuesta(cadena);
        computarTodosLosProcesamientos(q0,0);
        return respuesta.isAccepted();
    }
    
     /**
     * Función que procesa una cadena en el automata, e imprime los pasos para aceptar la cadena
     * @param cadena Cadena a procesar
     * @return boolean - True si la cadena ingresada es aceptada, false de otra forma.
     */
    public boolean procesarCadenaConDetalles(String cadena){
        if(cadena.length()==0) {
            if(this.F.contains(q0)) {
		return true;
            }
            return false;
        }
        this.cadena=cadena;
        this.print = new Integer[cadena.length()];
        respuesta = new NDRespuesta(cadena);
        computarTodosLosProcesamientos(q0,0);
        if(respuesta.isAccepted()){
            System.out.print(respuesta.getAccepted().get(0)+" ");
        }else if(respuesta.getRejected().size()>0){
            System.out.print(respuesta.getRejected().get(0)+" ");
        }else if(respuesta.getAborted().size()>0){
            System.out.print(respuesta.getAborted().get(0) + " Aborted ");
        }
        return respuesta.isAccepted();
    }
    
     /**
     * Función que procesa una cadena en el automata, e imprime todos los posibles procesamientos
     * @param cadena Cadena a procesar
     * @return boolean - True si la cadena ingresada es aceptada, false de otra forma.
     */
    public int computarTodosLosProcesamientos(String cadena){
        if(cadena.length()==0) {
            if(this.F.contains(q0)) {
                respuesta.addAceptado(q0);
		return 1;
            }
            respuesta.addRechazado(q0);
            return 1;
        }
        this.cadena=cadena;
        this.print = new Integer[cadena.length()];
        respuesta = new NDRespuesta(cadena);
        computarTodosLosProcesamientos(q0,0);
        
        System.out.println("\nAceptadas:");
        for(int i=0;i<respuesta.getAccepted().size();i++){
            System.out.println(respuesta.getAccepted().get(i));
        }
        System.out.println("Rechazadas:");
        for(int i=0;i<respuesta.getRejected().size();i++){
            System.out.println(respuesta.getRejected().get(i));
        }
        System.out.println("Abortadas:");
        for(int i=0;i<respuesta.getAborted().size();i++){
            System.out.println(respuesta.getAborted().get(i));
        }
        return respuesta.getAccepted().size() + respuesta.getRejected().size() + respuesta.getAborted().size();
    }
    
    private int computarTodosLosProcesamientos(int state, int letter){
	boolean pass=true;
	ArrayList<Tuple> automataState = this.Delta.get(state);
        
	for(int i=0; i<automataState.size();i++) {
            if(automataState.get(i).getSymbol().equals(this.cadena.substring(letter,letter+1))) {
		state = automataState.get(i).getFinalState();
		this.print[letter]=state;
                pass=false;
		if(letter<cadena.length()-1) {
                    state = computarTodosLosProcesamientos(state,letter+1);
		}if(letter==cadena.length()-1){
                    if(this.F.contains(state)) {
                        for(int j=0;j<this.cadena.length();j++) {
                            respuesta.addAceptado(this.print[j]);
                        }
                    }else {
                        for(int j=0;j<this.cadena.length();j++) {
                            respuesta.addRechazado(this.print[j]);
                        }
                    }
		}
            }
	}
	if(pass) {
            for(int j=0;j<letter;j++) {
                respuesta.addAbortado(this.print[j]);
            }
            respuesta.addAbortado(-1);
	}
        return state;
    }
    
     /**
     * Función que procesa una lista de cadenas en el automata, e imprime un procesamiento, cuantos procesamientos hay de cada tipo, y si es aceptada o no cada una de las cadenas
     * @param listaCadenas Lista de cadenas a procesar
     * @param nombreArchivo Nombre del archivo donde se guardarán los resultados obtenidos
     * @param imprimirPantalla Booleano que indica si se debe imprimir la respuesta en consola o no
     */
    public void procesarListaCadenas(String[] listaCadenas, String nombreArchivo, boolean imprimirPantalla){
        FileWriter fichero1 = null;
        PrintWriter pw1 = null;
        try
        {
            File nombre = new File(nombreArchivo);
            if(!nombre.exists())nombre.createNewFile();
            fichero1 = new FileWriter(nombre);
            pw1 = new PrintWriter(fichero1);
            //pw1.println("C\tProc\tP\tAc\tAb\tR\tSi/no");
            for (int i = 0; i < listaCadenas.length; i++){
                this.cadena=listaCadenas[i];
                this.print = new Integer[cadena.length()];
                respuesta = new NDRespuesta(cadena);
                computarTodosLosProcesamientos(q0,0);
                
                String res=cadena+"\t";
                if(respuesta.isAccepted()){
                    res+=respuesta.getAccepted().get(0)+"\t";
                }else if(respuesta.getRejected().size()>0){
                    res+=respuesta.getRejected().get(0)+"\t";
                }else if(respuesta.getAborted().size()>0){
                    res+="Cadena abortada \t";
                }
                res+=Integer.toString(respuesta.getAccepted().size()+respuesta.getRejected().size()+respuesta.getAborted().size())+"\t"+Integer.toString(respuesta.getAccepted().size())+"\t";
                res+=Integer.toString(respuesta.getAborted().size())+"\t"+ Integer.toString(respuesta.getRejected().size())+"\t";
                if(respuesta.isAccepted()){
                    res+="Sí";
                }else{
                    res+="No";
                }
                pw1.println(res);
                if(imprimirPantalla){
                    System.out.println(res);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (null != fichero1)
              fichero1.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }

     /**
     * Función que imprime el automata
     */
    public void printAutomata() {
	System.out.println("El automata no determinista ingresado es:");
	for(int i=0;i<this.Delta.size();i++) {
            System.out.print("q"+i + ": ");
            for(int j=0;j<this.Delta.get(i).size();j++) {
		System.out.print(this.Delta.get(i).get(j).getSymbol()+",q" + this.Delta.get(i).get(j).getFinalState() + " ");
            }
            System.out.println();
        }
	System.out.println();
	if(this.F.size()>0) {
            System.out.println("Y sus estados de aceptación son:");
            for(int i=0;i<this.F.size();i++) {
		System.out.print("q"+F.get(i) + " ");
            }
	}else {
            System.out.println("Aún no tiene estados de aceptación");
	}
	System.out.println();
	System.out.println();
    }
}