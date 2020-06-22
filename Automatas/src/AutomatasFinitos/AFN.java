package AutomatasFinitos;

import Herramientas.Transition;
import Herramientas.TransitionAFN;
import Herramientas.Transitions;
import LectoresYProcesos.InteraccionesAutomas;
import java.util.ArrayList;
import Herramientas.Tuple;
import LectoresYProcesos.InteraccionesAutomas.Lecto;
import ProcesamientoCadenas.ProcesamientoCadenaAFN;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase es el automata finito no determinista
 * en este automata se puede realizar el procesamiento de cadenas sobre el automata ingresado, para saber si esta pertenece o no al lenguaje
 * mientras se muestran los pasos del procesamiento, si así se desea
 * @author jdacostabe
 */

public class AFN extends AFD{
    
    private int cont, numberIndex;
    private String cadena;
    private Integer[] print;
    private ProcesamientoCadenaAFN respuesta;
    private ArrayList<Integer> usefulStates;
    private String statesName;
    
    
    /**
     * Constructor de un AFN vacío
     */
    public AFN() {
    }
    
    /**
     * Constructor, inicializa los atributos.
     * @param Sigma Alfabeto
     * @param Q Conjunto de estados
     * @param q0 Estado inicial
     * @param F Estados de aceptación
     * @param Delta Transiciones
     */
    public AFN(Alfabeto Sigma, ArrayList<String> Q, Integer q0, ArrayList<Integer> F, Transitions Delta) {
        super(Sigma, Q, q0, F, Delta);
    }
    
    /**
     * Constructor, inicializa los atributos.
     * @param Sigma Alfabeto
     * @param Q Conjunto de estados
     * @param q0 Estado inicial
     * @param F Estados de aceptación
     * @param Delta Transiciones
     */
    public AFN(char[] Sigma, ArrayList<String> Q, String q0, ArrayList<String> F, ArrayList<Tuple> Delta) {
        int maxState=0;
        numberIndex=0;
        for(int i=0;i<q0.length();i++){
            if(isInteger(q0.substring(i))){
                numberIndex=i;
                break;
            }
        }
        this.statesName = q0.substring(0,numberIndex);
        for(int i=0;i<Q.size();i++)
            maxState=Math.max(maxState,parseInt(Q.get(i).substring(numberIndex)));
        
        this.Sigma = new Alfabeto(Sigma);
        this.Q = Q;
        this.q0 = parseInt(q0.substring(numberIndex));
        this.F = new ArrayList<>();
        for(int i=0;i<F.size();i++)
            this.F.add(parseInt(F.get(i).substring(numberIndex)));
        
        this.Delta = new TransitionAFN(maxState);
        for(int i=0;i<Delta.size();i++) {
            if(Delta.get(i).getFinalState()>maxState || Delta.get(i).getInitialState()>maxState){
                System.out.println("El número de estados ingresados no concuerda");
                return;
            }
            this.Delta.add(Delta.get(i).getInitialState(), Delta.get(i).getSymbol(), Delta.get(i).getFinalState());
        }
        this.estadosInaccesibles=new ArrayList<>();
        this.estadosLimbo=new ArrayList<>();
    }

    /**
     * Inicializa los atributos a partir del archivo de texto.
     * @param nombreArchivo Nombre del archivo donde está el automata a leer
     */
    public AFN(String nombreArchivo) throws Error, FileNotFoundException{
        Lecto lec = Lecto.inicio;
        ArrayList<Character> alpha = null;
        ArrayList<String> Q = null;
        String q0 = null;
        ArrayList<String> F = null;
        ArrayList<Tuple> Delta = null;
        int maxState=0;
        numberIndex=0;
        
        try {
            Scanner sca = new Scanner(new File(nombreArchivo));
            while(sca.hasNextLine()){
                String lin = sca.nextLine();
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
                                if(numberIndex==0){
                                    for(int i=0;i<lin.length();i++){
                                        if(isInteger(lin.substring(i))){
                                            numberIndex=i;
                                            break;
                                        }
                                    }
                                }
                                maxState=Math.max(maxState,parseInt(lin.substring(numberIndex)));
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
                                String[] origin3 = origin2[1].split(";");
                                for(int i=0;i<origin3.length;i++){
                                    String estado2 = origin3[i];
                                    if(!Q.contains(estado2))throw new Error("el estado de llegada debe pertenecer a los estados del automata");
                                    Delta.add(new Tuple(alpfa.substring(0), parseInt(estado1.substring(numberIndex)), parseInt(estado2.substring(numberIndex))));
                                }
                                break;
                            default:
                                break;
                        }
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        
        char[] ad =new char[alpha.size()];
        for (int i = 0; i < alpha.size(); i++) {
            ad[i]=alpha.get(i);
        }
        this.statesName = q0.substring(0,numberIndex);
        this.Sigma = new Alfabeto(ad);
        this.Q = Q;
        this.q0 = parseInt(q0.substring(numberIndex));
        this.F = new ArrayList<>();
        for(int i=0;i<F.size();i++)
            this.F.add(parseInt(F.get(i).substring(numberIndex)));
        
        this.Delta = new TransitionAFN(maxState);
        for(int i=0;i<Delta.size();i++) {
            if(Delta.get(i).getFinalState()>maxState || Delta.get(i).getInitialState()>maxState){
                System.out.println("El número de estados ingresados no concuerda");
                return;
            }
            this.Delta.add(Delta.get(i).getInitialState(),Delta.get(i).getSymbol(),Delta.get(i).getFinalState());
        }
        this.estadosInaccesibles=new ArrayList<>();
        this.estadosLimbo=new ArrayList<>();
    }
    
    /**
     * Función que procesa una cadena en el automata
     * @param cadena Cadena a procesar
     * @return boolean - True si la cadena ingresada es aceptada, false de otra forma.
     */
    @Override
    public boolean procesarCadena(String cadena){
        respuesta = new ProcesamientoCadenaAFN(cadena,statesName+Integer.toString(q0),statesName);
        if(cadena.length()==0) {
            if(this.F.contains(q0)) {
                respuesta.addAceptado(q0);
		return true;
            }
            respuesta.addRechazado(q0);
            return false;
        }
        this.cadena=cadena;
        this.print = new Integer[cadena.length()];
        computarTodosLosProcesamientos(q0,0);
        return respuesta.isAccepted();
    }
    
     /**
     * Función que procesa una cadena en el automata, e imprime los pasos para aceptar la cadena
     * @param cadena Cadena a procesar
     * @return boolean - True si la cadena ingresada es aceptada, false de otra forma.
     */
    @Override
    public boolean procesarCadenaConDetalles(String cadena){
        respuesta = new ProcesamientoCadenaAFN(cadena,statesName+Integer.toString(q0),statesName);
        this.cadena=cadena;
        this.print = new Integer[cadena.length()];
        if(cadena.length()==0) {
            //System.out.print("[q"+q0+",$] ");
            if(this.F.contains(q0)) {
		respuesta.addAceptado(q0);
            }else{
                respuesta.addRechazado(q0);
            }
        }else{
            computarTodosLosProcesamientos(q0,0);
        }
        if(respuesta.isAccepted()){
            System.out.println(respuesta.getAccepted().get(0)+"-> Aceptado");
        }else if(respuesta.getRejected().size()>0){
            System.out.println(respuesta.getRejected().get(0)+"-> Rechazado");
        }else if(respuesta.getAborted().size()>0){
            System.out.println(respuesta.getAborted().get(0) +"-> Abortado");
        }
        return respuesta.isAccepted();
    }
    
     /**
     * Función que procesa una cadena en el automata, e imprime todos los posibles procesamientos
     * @param cadena Cadena a procesar
     * @param nombreArchivo Nombre del archivo en el que se hará la impresión de los procesamientos
     * @return int - Devuelve el número de procesamientos obtenidos al procesar la cadena.
     */
    public int computarTodosLosProcesamientos(String cadena, String nombreArchivo){
        FileWriter fichero1 = null;
        PrintWriter pw1 = null;
        String archivo[]=nombreArchivo.split("\\.");
        try
        {
            this.cadena=cadena;
            this.print = new Integer[cadena.length()];
            respuesta = new ProcesamientoCadenaAFN(cadena,statesName+Integer.toString(q0),statesName);
            if(cadena.length()==0){
                if(this.F.contains(q0)){
                    respuesta.addAceptado(q0);
                }else{
                    respuesta.addRechazado(q0);
                }
            }else{
                computarTodosLosProcesamientos(q0,0);
            }
            String res="Aceptadas:\n";
            if(respuesta.getAccepted().isEmpty()){
                res+="\n";
            }
            for(int i=0;i<respuesta.getAccepted().size();i++){
                res+="  "+respuesta.getAccepted().get(i)+"\n";
            }
            File nombre;
            if(archivo.length==1){
                nombre = new File(nombreArchivo+"Aceptadas");
            }else{
                nombre = new File(archivo[0]+"Aceptadas."+archivo[1]);
            }
            if(!nombre.exists())nombre.createNewFile();
            fichero1 = new FileWriter(nombre);
            pw1 = new PrintWriter(fichero1);
            pw1.println("Para la cadena:"+cadena+"\n"+res);
            System.out.println(res);
            fichero1.close();
            
            res="Rechazadas:\n";
            if(respuesta.getRejected().isEmpty()){
                res+="\n";
            }
            for(int i=0;i<respuesta.getRejected().size();i++){
                res+="  "+respuesta.getRejected().get(i)+"\n";
            }
            
            if(archivo.length==1){
                nombre = new File(nombreArchivo+"Rechazadas");
            }else{
                nombre = new File(archivo[0]+"Rechazadas."+archivo[1]);
            }
            if(!nombre.exists())nombre.createNewFile();
            fichero1 = new FileWriter(nombre);
            pw1 = new PrintWriter(fichero1);
            pw1.println("Para la cadena:"+cadena+"\n"+res);
            System.out.println(res);
            fichero1.close();
            
            res="Abortadas:\n";
            if(respuesta.getAborted().isEmpty()){
                res+="\n";
            }
            for(int i=0;i<respuesta.getAborted().size();i++){
                res+="  "+respuesta.getAborted().get(i)+"\n";
            }
            if(archivo.length==1){
                nombre = new File(nombreArchivo+"Abortadas");
            }else{
                nombre = new File(archivo[0]+"Abortadas."+archivo[1]);
            }
            if(!nombre.exists())nombre.createNewFile();
            fichero1 = new FileWriter(nombre);
            pw1 = new PrintWriter(fichero1);
            pw1.println("Para la cadena:"+cadena+"\n"+res);
            System.out.println(res);
            
        } catch (Exception e) {
        } finally {
           try {
           if (null != fichero1)
              fichero1.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
        return respuesta.getAborted().size()+respuesta.getAccepted().size()+respuesta.getRejected().size();
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
    @Override
    public void procesarListaCadenas(String[] listaCadenas, String nombreArchivo, boolean imprimirPantalla){
        FileWriter fichero1 = null;
        PrintWriter pw1 = null;
        try
        {
            File nombre = new File(nombreArchivo);
            if(!nombre.exists())nombre.createNewFile();
            fichero1 = new FileWriter(nombre);
            pw1 = new PrintWriter(fichero1);
            for (int i = 0; i < listaCadenas.length; i++){
                this.cadena=listaCadenas[i];
                this.print = new Integer[cadena.length()];
                respuesta = new ProcesamientoCadenaAFN(cadena,statesName+Integer.toString(q0),statesName);
                if(listaCadenas[i].length()==0){
                    if(this.F.contains(q0)){
                        respuesta.addAceptado(q0);
                    }else{
                        respuesta.addRechazado(q0);
                    }
                }else{
                    computarTodosLosProcesamientos(q0,0);
                }
                
                String res=cadena;
                if(respuesta.isAccepted()){
                    res+="   "+respuesta.getAccepted().get(0)+"   ";
                }else if(respuesta.getRejected().size()>0){
                    res+="   "+respuesta.getRejected().get(0)+"   ";
                }else if(respuesta.getAborted().size()>0){
                    res+="   Cadena abortada    ";
                }
                res+= "Número de procesamientos: ["+ Integer.toString(respuesta.getAccepted().size()+respuesta.getRejected().size()+respuesta.getAborted().size()) +"]   Procesamientos aceptados: ["+Integer.toString(respuesta.getAccepted().size())+"]   ";
                res+= "Procesamientos rechazados: ["+ Integer.toString(respuesta.getRejected().size())+"]   Procesamientos abortados["+Integer.toString(respuesta.getAborted().size())+"]   ";
                if(respuesta.isAccepted()){
                    res+="La cadena si fue aceptada";
                }else{
                    res+="La cadena no fue aceptada";
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
     * Función que realiza la conversión del AFN actual al AFD respectivo
     * @return AFD - Automata Finito Determinista equivalente al AFN que llama la función
     */
    public AFD AFNtoAFD() {
	ArrayList<Integer> states = new ArrayList<>(), detAcceptance = new ArrayList<>();
	ArrayList<Tuple> newStates = new ArrayList<>(), deterministicStates;
        ArrayList<ArrayList<Tuple>> automataDeterminista = new ArrayList<>();
	int numberNewState=this.Delta.size(), cont=0;
        usefulStates = new ArrayList<>();
        
        for(int i=0;i<this.F.size();i++) {
            detAcceptance.add(this.F.get(i));
	}
	
        for(int i=0;i<this.Delta.size();i++) {
            deterministicStates = new ArrayList<>();
            for(int j=0;j<Sigma.length();j++) {
		states.clear();
                for(int k=0;k<this.Delta.get(i).size();k++) {
                    if(this.Delta.get(i).get(k).getSymbol().equals(String.valueOf(Sigma.get(j)))){
			states.add(this.Delta.get(i).get(k).getFinalState());
                    }
                }
                Collections.sort(states);
                
                if(states.size()>0){
                    String statesString = states.get(0).toString();
                    for(int a=1;a<states.size();a++){
			statesString += " " + states.get(a).toString();
                    }
                    if(states.size()>1) {
                        int value = -1;
                        for(int l=0;l<newStates.size();l++){
                            if(newStates.get(l).getSymbol().equals(statesString)) {
                                value =  newStates.get(l).getFinalState();
                                break;
                            }
                        }
                        if(value>=0) {
                            deterministicStates.add(new Tuple(String.valueOf(Sigma.get(j)),value));
			}else{
                            newStates.add(new Tuple(statesString,numberNewState)); //(Se guarda la cadena de valores a los que reperesenta (En el simbolo) y el numero de estado que es (Como el estado final))
                            deterministicStates.add(new Tuple(String.valueOf(Sigma.get(j)),numberNewState));
                            numberNewState++;
			}
                    }else{
			deterministicStates.add(new Tuple(String.valueOf(Sigma.get(j)),states.get(0)));
                    }
		}
            }
            automataDeterminista.add(deterministicStates);
	}
        
        for(int i=0;i<newStates.size();i++) {
            deterministicStates = new ArrayList<>();
            ArrayList<Integer> combinationOf = stringToValues(newStates.get(i).getSymbol());
            for(int l=0;l<Sigma.length();l++) {
		states.clear();
                for(int j=0;j<combinationOf.size();j++){
                    for(int k=0;k<automataDeterminista.get(combinationOf.get(j)).size();k++){
			if(automataDeterminista.get(combinationOf.get(j)).get(k).getSymbol().equals(String.valueOf(Sigma.get(l)))) {
                            if(automataDeterminista.get(combinationOf.get(j)).get(k).getFinalState()<this.Delta.size()) {
				states.add(automataDeterminista.get(combinationOf.get(j)).get(k).getFinalState());
                            }else{
				ArrayList<Integer> aux = stringToValues(search(automataDeterminista.get(combinationOf.get(j)).get(k).getFinalState(), newStates));
				for(int m=0;m<aux.size();m++) {
                                    states.add(aux.get(m));
				}
                            }
			}
                    }
                }
                Set<Integer> hashSet = new HashSet<>(states);
		states.clear();
		states.addAll(hashSet);
                Collections.sort(states);
		
                if(states.size()>0){
		    String statesString=states.get(0).toString();
                    for(int a=1;a<states.size();a++) {
			statesString = statesString+ " " +states.get(a).toString() ;
                    }
                    if(states.size()>1) {
                        int value = -1;
                        for(int m=0;m<newStates.size();m++){
                            if(newStates.get(m).getSymbol().equals(statesString)) {
                                value =  newStates.get(m).getFinalState();
                                break;
                            }
                        }
			if(value>=0) {
                            deterministicStates.add(new Tuple(String.valueOf(Sigma.get(l)),value));
                        }else{
                            newStates.add(new Tuple(statesString,numberNewState));
                            deterministicStates.add(new Tuple(String.valueOf(Sigma.get(l)),numberNewState));
                            numberNewState++;
			}
                    }else{
                        deterministicStates.add(new Tuple(String.valueOf(Sigma.get(l)),states.get(0)));
                    }
		}
            }
            automataDeterminista.add(deterministicStates);
	}
	for(int i=0;i<newStates.size();i++){
            ArrayList<Integer> auxArray = stringToValues(newStates.get(i).getSymbol());
            for(int j=0;j<auxArray.size();j++) {
		if(detAcceptance.contains(auxArray.get(j))) {
                    detAcceptance.add(newStates.get(i).getFinalState());
                    break;
		}
            }
        }
        System.out.println("Tabla con las transiciones de la transformación AFN a AFD");
        printTableAFNtoAFD(automataDeterminista, detAcceptance, newStates);
        
        ArrayList<String> newQ = new ArrayList<>();
        Transition newDelta = new Transition();
        boolean hasFinalState;
        for(int i=0;i<automataDeterminista.size();i++) {
            if(Q.contains(statesName+i)){
                newQ.add(statesName+i);
                for(int j=0;j<Sigma.length();j++){
                    hasFinalState = true;
                    for(int k=0;k<automataDeterminista.get(i).size();k++){
                        if(Sigma.get(j)==automataDeterminista.get(i).get(k).getSymbol().charAt(0)){
                            newDelta.add(Sigma.get(j), statesName+i, statesName+automataDeterminista.get(i).get(k).getFinalState());
                            hasFinalState = false;
                            break;
                        }
                    }
                    if(hasFinalState){
                        newDelta.add(Sigma.get(j), statesName+i, statesName+numberNewState);
                    }
                }
            }else if(!"".equals(search(i,newStates))){
                newQ.add(statesName+i);
                for(int j=0;j<Sigma.length();j++){
                    hasFinalState = true;
                    for(int k=0;k<automataDeterminista.get(i).size();k++){
                        if(Sigma.get(j)==automataDeterminista.get(i).get(k).getSymbol().charAt(0)){
                            newDelta.add(Sigma.get(j), statesName+i, statesName+automataDeterminista.get(i).get(k).getFinalState());
                            hasFinalState = false;
                            break;
                        }
                    }
                    if(hasFinalState){
                        newDelta.add(Sigma.get(j), statesName+i, statesName+numberNewState);
                    }
                }
            }
        }
        newQ.add(statesName+numberNewState);
        for(int i=0;i<Sigma.length();i++){
            newDelta.add(Sigma.get(i), statesName+numberNewState, statesName+numberNewState);
        }
        /*String[] States;
        String res="";
        
        ArrayList<String> newQ = new ArrayList<>();
        Transition newDelta = new Transition();
        boolean hasFinalState;
        for(int i=0;i<automataDeterminista.size();i++) {
            if(Q.contains(statesName+i)){
                newQ.add(statesName+i);
                for(int j=0;j<Sigma.length();j++){
                    hasFinalState = true;
                    for(int k=0;k<automataDeterminista.get(i).size();k++){
                        if(Sigma.get(j)==automataDeterminista.get(i).get(k).getSymbol().charAt(0)){
                            if(Q.contains(statesName+automataDeterminista.get(i).get(k).getFinalState())){
                                res = statesName+automataDeterminista.get(i).get(k).getFinalState();
                            }else if(!"".equals(search(automataDeterminista.get(i).get(k).getFinalState(),newStates))){
                                States = search(automataDeterminista.get(i).get(k).getFinalState(),newStates).split(" ");
                                res="{"+statesName+States[0];
                                for(int l=1;l<States.length-1;l++){
                                    res+=","+statesName+States[l];
                                }
                                res+=","+statesName+States[States.length-1]+"}";
                            }
                            newDelta.add(Sigma.get(j), statesName+i, res);
                            hasFinalState = false;
                            break;
                        }
                    }
                    if(hasFinalState){
                        newDelta.add(Sigma.get(j), statesName+i, statesName+numberNewState);
                    }
                }
            }else if(!"".equals(search(i,newStates))){
                States = search(i,newStates).split(" ");
                res="{"+statesName+States[0];
                for(int j=1;j<States.length-1;j++){
                    res+=","+statesName+States[j];
                }
                res+=","+statesName+States[States.length-1]+"}";
                newQ.add(res);
                for(int j=0;j<Sigma.length();j++){
                    hasFinalState = true;
                    for(int k=0;k<automataDeterminista.get(i).size();k++){
                        if(Sigma.get(j)==automataDeterminista.get(i).get(k).getSymbol().charAt(0)){
                            if(Q.contains(statesName+automataDeterminista.get(i).get(k).getFinalState())){
                                res = statesName+automataDeterminista.get(i).get(k).getFinalState();
                            }else if(!"".equals(search(automataDeterminista.get(i).get(k).getFinalState(),newStates))){
                                States = search(automataDeterminista.get(i).get(k).getFinalState(),newStates).split(" ");
                                res="{"+statesName+States[0];
                                for(int l=1;l<States.length-1;l++){
                                    res+=","+statesName+States[l];
                                }
                                res+=","+statesName+States[States.length-1]+"}";
                            }
                            newDelta.add(Sigma.get(j), statesName+i, res);
                            hasFinalState = false;
                            break;
                        }
                    }
                    if(hasFinalState){
                        newDelta.add(Sigma.get(j), statesName+i, statesName+numberNewState);
                    }
                }
            }
        }
        newQ.add(statesName+numberNewState);
        for(int i=0;i<Sigma.length();i++){
            newDelta.add(Sigma.get(i), statesName+numberNewState, statesName+numberNewState);
        }*/
        
        AFD newDet = new AFD(Sigma, newQ, q0, detAcceptance, newDelta);
        newDet.eliminarEstadosInaccesibles();
        return newDet;
    }
    
    private ArrayList<Integer> stringToValues(String cadena) {
        String[] values = cadena.split(" ");
        ArrayList<Integer> combinationOf = new ArrayList<>();
        for(int i=0;i<values.length;i++){
            combinationOf.add(Integer.parseInt(values[i]));
        }
	return combinationOf;
    }
    
    private String search(Integer value, ArrayList<Tuple> newStates) {
        for(int i=0;i<newStates.size();i++) {
            if(Objects.equals(newStates.get(i).getFinalState(), value)) {
                return newStates.get(i).getSymbol();
            }
        }
        return "";
    }

    private void printStateEquivalence(ArrayList<Tuple> newStates) {
	ArrayList<Integer> auxArray;
	System.out.println("Los estados añadidos representan los conjuntos de estados:");
	for(int i=0;i<newStates.size();i++) {
            System.out.print(statesName+newStates.get(i).getFinalState()+": ");
            auxArray = stringToValues(newStates.get(i).getSymbol());
            for(int j=0;j<auxArray.size();j++) {
		System.out.print(statesName+auxArray.get(j)+" ");
            }
            System.out.println();
        }
	System.out.println();
    }
    
    private void printTableAFNtoAFD(ArrayList<ArrayList<Tuple>> automataDeterminista, ArrayList<Integer> detAcceptance,ArrayList<Tuple> newStates) {
	String[] States;
        String res = "Δ";
        boolean hasFinal;
        for(int i=0;i<Sigma.length();i++){
            res+="\t"+Sigma.get(i);
        }
        res+="\n";
	for(int i=0;i<automataDeterminista.size();i++) {
            if(Q.contains(statesName+i)){
                res+=statesName+i + "\t";
            }else if(!"".equals(search(i,newStates))){
                States = search(i,newStates).split(" ");
                res+="{"+statesName+States[0];
                for(int j=1;j<States.length-1;j++){
                    res+=","+statesName+States[j];
                }
                res+=","+statesName+States[States.length-1]+"}\t";
            }else{
                continue;
            }
            
            for(int j=0;j<Sigma.length();j++){
                hasFinal = true;
                for(int k=0;k<automataDeterminista.get(i).size();k++){
                    if(automataDeterminista.get(i).get(k).getSymbol().charAt(0) == Sigma.get(j)){
                        if(Q.contains(statesName+automataDeterminista.get(i).get(j).getFinalState())){
                            res+=statesName + automataDeterminista.get(i).get(j).getFinalState();
                        }else{
                            States = search(automataDeterminista.get(i).get(j).getFinalState(),newStates).split(" ");
                            res+="{"+statesName+States[0];
                            for(int l=1;l<States.length-1;l++){
                                res+=","+statesName+States[j];
                            }
                            res+=","+statesName+States[States.length-1]+"}";
                        }
                        hasFinal = false;
                        break;
                    }
                }
                if(hasFinal){
                    res+="∅";
                }
                res+="\t";
            }
            res+="\n";
	}
        System.out.println(res);
    }
    
    /**
     * Función que busca los estados inaccesibles del automata y los guarda en una variable
     */
    @Override
    public void hallarEstadosInaccesibles(){
        estadosInaccesibles.add(statesName+q0);
        identificarEstadosAccesibles(q0);
        ArrayList<String> estadosAccesibles = new ArrayList<>(estadosInaccesibles);
        estadosInaccesibles.clear();
        for(int i=0;i<Q.size();i++){
            if(!estadosAccesibles.contains(Q.get(i))){
                estadosInaccesibles.add(Q.get(i));
            }
        }
    }
    
    private void identificarEstadosAccesibles(int State) {
        Integer Stadogo;
        for(int i=0;i<Delta.get(State).size();i++){
            Stadogo = Delta.get(State).get(i).getFinalState();
            if(State!=Stadogo && !estadosInaccesibles.contains(statesName+Stadogo)){
                estadosInaccesibles.add(statesName+Stadogo);
                identificarEstadosAccesibles(Stadogo);
            }
        }
    }
    
    /**
     * Función que procesa una cadena dada en el AFD equivalente del AFN que hace el llamado de la función
     * @param cadena Cadena a procesar
     * @return boolean - True si la cadena es aceptada, False de otra forma
     */
    public boolean procesarCadenaConversion(String cadena) {
        return AFNtoAFD().procesarCadena(cadena);
    }

    /**
     * Función que procesa una cadena dada en el AFD equivalente del AFN que hace el llamado de la función, e imprime el procesamiento de esta
     * @param cadena Cadena a procesar
     * @return boolean - True si la cadena es aceptada, False de otra forma
     */
    public boolean procesarCadenaConDetallesConversion(String cadena) {
        return AFNtoAFD().procesarCadenaConDetalles(cadena);
    }

    /**
     * Función que procesa una lista de cadenas dada en el AFD equivalente del AFN que hace el llamado de la función
     * @param listaCadenas Lista de cadenas a procesar
     * @param nombreArchivo Nombre del archivo donde se hace la impresión del resultado
     * @param imprimirPantalla Booleano que indica si se imprime el resultado en pantalla o no
     */
    public void procesarListaCadenasConversion(String[] listaCadenas, String nombreArchivo, boolean imprimirPantalla) {
        AFNtoAFD().procesarListaCadenas(listaCadenas, nombreArchivo, imprimirPantalla);
    }
    
    /**
     * Función que retorna una cadena con toda la información del automata que hace el llamado de la función
     * @return String - Cadena que contiene toda la información del automata actual
     */
    @Override
    public String toString(){
        String cadena="!nfa\n";
        cadena+=Sigma.toString();
        cadena+="#states\n";
        for (int i=0;i<Q.size();i++) {
            cadena+=Q.get(i)+"\n";
        }
        cadena+="#initial\n"+statesName+q0+"\n"+"#accepting\n";
        for (int i=0;i<F.size();i++) {
            cadena+=statesName+F.get(i)+"\n";
        }
        cadena += "#transitions\n";
        boolean aux;
        for(int i=0;i<Q.size();i++) {
            int value = parseInt(Q.get(i).substring(numberIndex));
            for(int j=0;j<Sigma.length();j++){
                aux = true;
                for(int k=0;k<Delta.get(value).size(); k++) {
                    if(aux && Delta.get(value).get(k).getSymbol().charAt(0) == Sigma.get(j)){
                        cadena+=statesName+value+":"+Sigma.get(j)+">"+statesName+Delta.get(i).get(k).getFinalState();
                        aux = false;
                    }else if(Delta.get(value).get(k).getSymbol().charAt(0) == Sigma.get(j)){
                        cadena+=";"+statesName+Delta.get(i).get(k).getFinalState();
                    }
                }
                if(!aux){
                    cadena+="\n";
                }
            }
	}
        return cadena;
    }
    
    private boolean isInteger(String cadena){
        try{
            Integer.parseInt(cadena);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
    
    /**
     * Función que imprime en consola una lista de procesamientos indicada, de una cadena dada
     * @param cadena cadena a procesar
     * @param computacion lista de procesamientos que se desea imprimir 0 - Aceptados, 1 - Rechazados, 2 - Abortados
     */
    public void imprimirComputaciones(String cadena, int computacion) {
        procesarCadena(cadena);
        ArrayList<String> lista;
        switch (computacion) {
            case 0:
                lista = respuesta.getAccepted();
                break;
            case 1:
                lista = respuesta.getRejected();
                break;
            default:
                lista = respuesta.getAborted();
                break;
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i));
        }
    }
}