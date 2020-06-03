package AutomatasFinitos;

import LectoresYProcesos.CreadorAutomata;
import java.util.ArrayList;
import Herramientas.Tuple;
import ProcesamientoCadenas.ProcesamientoCadenaAFN;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.util.Collections;
import java.util.HashSet;
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

public class AFN extends Automat{
    
     /**
     * El atributo Sigma representa el alfabeto del automata.
     */
    public final char[] Sigma; 
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
    
    private String cadena;
    private Integer[] print;
    private ProcesamientoCadenaAFN respuesta;
    private ArrayList<Integer> usefulStates;
    
    /**
     * Constructor, inicializa los atributos.
     * @param Sigma Alfabeto
     * @param Q Conjunto de estados
     * @param q0 Estado inicial
     * @param F Estados de aceptación
     * @param Delta Transiciones
     */
    public AFN(char[] Sigma, ArrayList<String> Q, String q0, ArrayList<String> F, ArrayList<Tuple> Delta){
        this.Sigma = Sigma;
        this.Q = Q;
        this.q0 = parseInt(q0);
        this.F = new ArrayList<>();
        for(int i=0;i<F.size();i++)
            this.F.add(parseInt(F.get(i).substring(1)));
        
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
    }

    /**
     * Inicializa los atributos a partir del archivo de texto.
     * @param nombreArchivo Nombre del archivo donde está el automata a leer
     */
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
                                String[] origin3 = origin2[1].split(";");
                                for(int i=0;i<origin3.length;i++){
                                    String estado2 = origin3[i];
                                    if(!Q.contains(estado2))throw new Error("el estado de llegada debe pertenecer a los estados del automata");
                                    Delta.add(new Tuple(alpfa.substring(0), parseInt(estado1.substring(1)), parseInt(estado2.substring(1))));
                                }
                                break;
                            default:
                                break;
                        }
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
        this.q0 = parseInt(q0.substring(1));
        this.F = new ArrayList<>();
        for(int i=0;i<F.size();i++)
            this.F.add(parseInt(F.get(i).substring(1)));
        
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
    }
    
    /**
     * Función que procesa una cadena en el automata
     * @param cadena Cadena a procesar
     * @return boolean - True si la cadena ingresada es aceptada, false de otra forma.
     */
    public boolean procesarCadena(String cadena){
        respuesta = new ProcesamientoCadenaAFN(cadena,"Q"+Integer.toString(q0));
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
    public boolean procesarCadenaConDetalles(String cadena){
        respuesta = new ProcesamientoCadenaAFN(cadena,"Q"+Integer.toString(q0));
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
            respuesta = new ProcesamientoCadenaAFN(cadena,"Q"+Integer.toString(q0));
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
                respuesta = new ProcesamientoCadenaAFN(cadena,"Q"+Integer.toString(q0));
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
    
    public void toDeterministic(String nombreArchivo) {
	ArrayList<Integer> states = new ArrayList<>(), detAcceptance = new ArrayList<>();
	ArrayList<Tuple> newStates = new ArrayList<>(), deterministicStates; //Los nuevos estados que se van generando por el conjunto de estado al que llegan los estados
        ArrayList<ArrayList<Tuple>> automataDeterminista = new ArrayList<>(); //DeltaDet (Transiciones del automata determinista)
	int numberNewState=this.Delta.size(), cont=0;
        usefulStates = new ArrayList<>();
        
        //Se incluyen los estados que ya son de aceptación a los estados de aceptación del automata determinista
	for(int i=0;i<this.F.size();i++) {
            detAcceptance.add(this.F.get(i));
	}
	
        //Por cada uno de los estados del AFN, revisamos cada una de sus transiciones
	for(int i=0;i<this.Delta.size();i++) {
            deterministicStates = new ArrayList<>();
            //Hacemos la evaluación por simbolo del alfabeto
            for(int j=0;j<Sigma.length;j++) {
		states.clear();
                //Por cada simbolo, revisamos a cuantos estados va el estado i, y los añadimos a un arraylist
		for(int k=0;k<this.Delta.get(i).size();k++) {
                    if(this.Delta.get(i).get(k).getSymbol().equals(String.valueOf(Sigma[j]))){
			states.add(this.Delta.get(i).get(k).getFinalState());
                    }
                }
                //Hacemos un sort de los estados para que queden en orden y poder manejarlos mas adelante
		Collections.sort(states);
                
                //Si con el simbolo que se está revisando hay estados de llegada, se busca la string del conjunto de strings de llegada {Q1,Q2} de ser necesario
		if(states.size()>0){
                    String statesString = states.get(0).toString();
                    for(int a=1;a<states.size();a++){
			statesString += " " + states.get(a).toString();
                    }
                    //Si hay más de un estado de llegada, se revisa si el conjunto de estados de llegada (Por ejemplo: {Q1,Q2}) ya existe como un nuevo estado
                    //Si solo hay un estado de llegada, se añade la transición del estado inical, al estado final (Se hace una copia de la transición del automata original)
                    if(states.size()>1) {
                        int value = -1;
                        for(int l=0;l<newStates.size();l++){
                            if(newStates.get(l).getSymbol().equals(statesString)) {
                                value =  newStates.get(l).getFinalState();
                                break;
                            }
                        }
                        //Si no existe, se añade el estado a los nuevos estados (Y se aumenta el numero del siguiente estado), y se hace la transicion del estado inicial al "nuevo estado" del automata determinista
                        //Si existe, simplemente se añade la nueva transición
			if(value>=0) {
                            deterministicStates.add(new Tuple(String.valueOf(Sigma[j]),value));
			}else{
                            newStates.add(new Tuple(statesString,numberNewState)); //(Se guarda la cadena de valores a los que reperesenta (En el simbolo) y el numero de estado que es (Como el estado final))
                            deterministicStates.add(new Tuple(String.valueOf(Sigma[j]),numberNewState));
                            numberNewState++;
			}
                    }else{
			deterministicStates.add(new Tuple(String.valueOf(Sigma[j]),states.get(0)));
                    }
		}
            }
            //Se añaden las transiciones (Una por símbolo que tenga al menos un estado de llegada) de cada estado a las transiciones del AFD
            automataDeterminista.add(deterministicStates);
	}
        
        //Una vez hemos añadido todas las transiciones de los estados originales del AFN, ahora debemos añadir las transiciones de los conjuntos de estados nuevos
        //Se utiliza un procedimiento similar al realizado anteriormente, sin embargo, hay diferencias (Que se marcan con los comentarios)        
	//Por cada uno de los nuevos estados ingresados, se miran por cada símbolo del alfabeto a que estado o conjunto de estados llega
	for(int i=0;i<newStates.size();i++) {
            deterministicStates = new ArrayList<>();
            //combinationOf es la lista de estados que componen el conjunto de estados de llegada
            ArrayList<Integer> combinationOf = stringToValues(newStates.get(i).getSymbol());
            //Por cada símbolo del alfabeto se revisa a que estado o conjunto de estados llega el "nuevo estado"
            for(int l=0;l<Sigma.length;l++) {
		states.clear();
                //Se revisan los estados a los que llegan cada uno de los estados del "nuevo estado" con el respectivo simbolo y se añaden al conjunto de estados al que llega el estado con el símbolo
		for(int j=0;j<combinationOf.size();j++){
                    for(int k=0;k<automataDeterminista.get(combinationOf.get(j)).size();k++){
			if(automataDeterminista.get(combinationOf.get(j)).get(k).getSymbol().equals(String.valueOf(Sigma[l]))) {
                            //Si llega a un estado original del AFN, lo añade. Si llega a un conjunto de estados
                            if(automataDeterminista.get(combinationOf.get(j)).get(k).getFinalState()<this.Delta.size()) {
				states.add(automataDeterminista.get(combinationOf.get(j)).get(k).getFinalState());
                            }else{
                                //Revisa el conjunto de estados a los que hace referencia el nuevo estado y los añade a los estados de llega del "nuevo estado" actual
                                //No revisa si hay un estado repetido (Eso se hace más adelante)
				ArrayList<Integer> aux = stringToValues(search(automataDeterminista.get(combinationOf.get(j)).get(k).getFinalState(), newStates));
				for(int m=0;m<aux.size();m++) {
                                    states.add(aux.get(m));
				}
                            }
			}
                    }
                }
                //Se borran los estados repetidos y se ordenan los estados
                Set<Integer> hashSet = new HashSet<>(states);
		states.clear();
		states.addAll(hashSet);
                Collections.sort(states);
		
                //Se hace exactamente lo mismo que con los primeros estados
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
                            deterministicStates.add(new Tuple(String.valueOf(Sigma[l]),value));
                        }else{
                            newStates.add(new Tuple(statesString,numberNewState));
                            deterministicStates.add(new Tuple(String.valueOf(Sigma[l]),numberNewState));
                            numberNewState++;
			}
                    }else{
                        deterministicStates.add(new Tuple(String.valueOf(Sigma[l]),states.get(0)));
                    }
		}
            }
            automataDeterminista.add(deterministicStates);
	}
	//Ya se tienen todas las transiciones del automata determinista, ahora hallaremos los estados de aceptación del mismo
	for(int i=0;i<newStates.size();i++){
            ArrayList<Integer> auxArray = stringToValues(newStates.get(i).getSymbol());
            //Si alguno de los estados a los que "representa" el nuevo estado, ya se encuentra en la lista de estados aceptados (Que se llenó previamente con los estados inicales del AFN)
            //Entonces se añade el nuevo estado a los estados de aceptación
            for(int j=0;j<auxArray.size();j++) {
		if(detAcceptance.contains(auxArray.get(j))) {
                    detAcceptance.add(newStates.get(i).getFinalState());
                    break;
		}
            }
        }
        //Cuando ya tenemos estos datos, buscamos y eliminamos los estados a los que no se llegan desde el estado inicial
        //Primero se recorre todo el automata y se llena una lista de estados por los que se pasa
	usefulStates.add(q0);
	identifyUsefulStates(q0, automataDeterminista);
	//Luego, esa lista se ordena de menor a mayor
	Collections.sort(usefulStates);
	
        //Teniendo los estados que se utilizan realmente, ahora hacemos la reducción del automata
        //A lo mejor no toca recorrer todo el automata en este ciclo (Solo se necesitaria revisar los estados iniciales del AFN)-----------------------------------------------------
	//Revisar ingresando un automata determinista como uno no determinista cuyo estado mayor no sea utilizado --------------------------------------------------------------------
        for(int i=0;i<automataDeterminista.size();i++) {
            //Si el estado actual no está en la lista de estados utiles, se elimina de la lista de estados aceptados y se aumenta el contador
            //Si el contador es 0 y el estado actual se está usando, no se hace nada porque todos los estados anteriores son utilizados y las transiciones se mantienen intactas
            if(!usefulStates.contains(i)) {
		if(detAcceptance.contains(i)) {
                    detAcceptance.remove(new Integer(i));
		}
		cont++;
            }else if(cont>0) {
                //Ya que el estado actual es útil y el contador actual es diferente de cero, hay estados anteriores que no son utilizados
                //Por lo cual, se debe correr todos los estados el número de estados que indique el contador
                //Para ello, empezamos con las transiciones (Cambiamos el número de los estados, y su numero en las transiciones)
                //Se inicia el ciclo en el valor actual de i, y se corren todos los estados a la derecha el numero de posiciones necesario a la izquierda para quitar lo estados que no se utilizan
                for(int j=i;j<automataDeterminista.size();j++) {
                    //Se corre el estado el número de posiciones indicado por cont
                    automataDeterminista.set(j-cont,automataDeterminista.get(j));
                    //Se buscan los estados nuevos y se les resta el numero de posiciones (El conjunto de estados a los que representa queda intacto)
                    for(int k=0;k<newStates.size();k++) {
                        if(newStates.get(k).getFinalState() == j) {
                            newStates.set(k, new Tuple(newStates.get(k).getSymbol(),j-cont));
                        }
                    }
                    //Se buscan los estados de aceptacion y se les resta el numero de posiciones (Si el estado existe en los estados de acpetación)
                    for(int k=0;k<detAcceptance.size();k++) {
                        if(detAcceptance.get(k)==j) {
                            detAcceptance.set(k,j-cont);
                        }
                    }
                }
                //Se quitan todas las posiciones finales de las transiciones de AFD
                for(int j=0;j<cont;j++) {
                    automataDeterminista.remove(automataDeterminista.size()-1);
                }
                //Se realiza el cambio tambien en los estados de llegada de las transiciones
                for(int j=0;j<automataDeterminista.size();j++){
                    for(int k=0;k<automataDeterminista.get(j).size();k++) {
                        if(automataDeterminista.get(j).get(k).getFinalState()>=i) {
                            automataDeterminista.get(j).set(k,new Tuple(automataDeterminista.get(j).get(k).getSymbol(),automataDeterminista.get(j).get(k).getFinalState()-cont));
                        }
                    }
                }
                //Se reinicia el contador
                cont=0;
            }
        }
        //Si cont es diferente de 0, hay estados que no es están usando y están al final de las transiciones del AFD, por lo cual se deben borrar (En la mayoria de los casos esto no pasará)
        for(int j=0;j<cont;j++) {
            automataDeterminista.remove(automataDeterminista.size()-1);
        }
        
	printDeterministic(automataDeterminista, detAcceptance);
	printStateEquivalence(newStates);
		
        //Se ingresa el nombre del archivo (YA SE TIENE EL AUTOMATA FINITO DETERMINISTA, PERO TODAVIA NO SE HA HECHO EL ARCHIVO, 
        //ESTE SE HARÁ DE ACUERDO A LAS INDICACIONES DEL PROFESOR CUANDO LAS DE), y alli se guarda el archivo con el automata (El cual se le pasa al AFD)
        
	/*AFD newDet;
        try {
            newDet = new AFD(nombreArchivo);
        } catch (Error ex) {
            Logger.getLogger(AFN.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AFN.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
	return newDet;*/
        return;
    }
    
    //Funcion que devuleve un arraylist con los valores de la cadena
    //---------------- Cambiar si se cambia para que las cadenas tengan el estado (Q o q)
    private ArrayList<Integer> stringToValues(String cadena) {
        String[] values = cadena.split(" ");
        ArrayList<Integer> combinationOf = new ArrayList<>();
        for(int i=0;i<values.length;i++){
            combinationOf.add(Integer.parseInt(values[i]));
        }
	return combinationOf;
    }
    
    //Funcion que se encarga de buscar el conjunto de estados que representa un estado nuevo
    private String search(int value, ArrayList<Tuple> newStates) {
        for(int i=0;i<newStates.size();i++) {
            if(newStates.get(i).getFinalState()== value) {
                return newStates.get(i).getSymbol();
            }
        }
        return "";
    }

    //Funcion recursiva que recorre todos los posibles caminos del automata determinista y guarda los estados que efectivamente se utilizan en el automata
    private void identifyUsefulStates(int state, ArrayList<ArrayList<Tuple>> automataDeterminista) {
	ArrayList<Tuple> automataState = automataDeterminista.get(state);
        //Se evalúan cada una de las transiciones del estado ingresado en el conjunto de transiciones automataDeterminista
	for(int i=0; i<automataState.size();i++) {
            //Si el estado final de la transición es un estado diferente al estado actual, y si el estado de llegada no se encuentra en
            //la lista de los estados utilizados, se añade el estado de llegada a esta lista y se identifica los estados de llegada del nuevo estado
            if(automataState.get(i).getFinalState() != state && !usefulStates.contains(automataState.get(i).getFinalState())) {
                usefulStates.add(automataState.get(i).getFinalState());
                identifyUsefulStates(automataState.get(i).getFinalState(),automataDeterminista);
            }
            //En cambio, si el estado final de la transición es el mismo estado y no se encuentra en la lista de estados utilizados, unicamente se añade a la lista de estados utilizados
            /*else if(automataState.get(j).getFinalState() == state && !usefulStates.contains(state)) {
                usefulStates.add(state);
            } ------------------------Si hay algun error al quitar los estados inutilizados, puede ser esto----------------------------------*/
	}
    }

    //Función para imprimir la equivalencia de estados entre el AFN y el AFD
    private void printStateEquivalence(ArrayList<Tuple> newStates) {
	ArrayList<Integer> auxArray;
	System.out.println("Los estados añadidos representan los conjuntos de estados:");
	for(int i=0;i<newStates.size();i++) {
            System.out.print("q"+newStates.get(i).getFinalState()+": ");
            auxArray = stringToValues(newStates.get(i).getSymbol());
            for(int j=0;j<auxArray.size();j++) {
		System.out.print("q"+auxArray.get(j)+" ");
            }
            System.out.println();
        }
	System.out.println();
    }
	
    //Función para imprimir todas las transiciones de un automata determinista obtenido
    private void printDeterministic(ArrayList<ArrayList<Tuple>> automataDeterminista, ArrayList<Integer> detAcceptance) {
	System.out.println("El automata determinista resultante es:");
	for(int i=0;i<automataDeterminista.size();i++) {
            System.out.print("q"+i + ": ");
            for(int j=0;j<automataDeterminista.get(i).size();j++) {
		System.out.print(automataDeterminista.get(i).get(j).getSymbol()+",q" + automataDeterminista.get(i).get(j).getFinalState() + " ");
            }
            System.out.println();
	}
	System.out.println();
	if(detAcceptance.size()>0) {
            System.out.println("Y sus estados de aceptación son:");
            for(int i=0;i<detAcceptance.size();i++) {
		System.out.print("q"+detAcceptance.get(i) + " ");
            }
	}else{
            System.out.println("El automata no determinista no tenia estados de aceptación");
	}
	System.out.println();
	System.out.println();
    }

    @Override
    public void ImprimirlambdaClausura_unEstado(int estado) {
       
    }

    @Override
    public void ImprimirlambdaClausura_variosEstado(ArrayList<Integer> estados) {
       
    }

    @Override
    public void imprimirComputaciones(String cadena, int computacion) {
        
    }
}