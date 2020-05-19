package AutomatasFinitos;

import Herramientas.NDRespuesta;
import java.util.Arrays;
import java.util.ArrayList;
import Herramientas.Tuple;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class AFN {
    public final char[] sigma; 
    public final int Q;
    public final int q0;
    public final ArrayList<Integer> F;
    public final ArrayList<ArrayList<Tuple>> Delta;
    
    int cont;
    String cadena;
    Integer[] print;
    NDRespuesta respuesta;

    public AFN(char[] sigma, int Q, int q0, ArrayList<Integer> F, ArrayList<Tuple> Delta){
        this.sigma = sigma;
        this.Q = Q;
        if(q0<=Q){
            this.q0 = q0;
        }else{
            this.q0 = 0;
            System.out.println("El estado inicial ingresado es mayor al numero de estados ingresado");
        }
        
        this.F = F;
        this.Delta = new ArrayList<>();
        
        while(this.Delta.size()<=Q) {
            this.Delta.add(new ArrayList<>());
        }
        for(int i=0;i<Delta.size();i++) {
            if(Delta.get(i).getFinalState()>Q || Delta.get(i).getInitialState()>Q){
                System.out.println("El número de estados ingresados no concuerda");
                return;
            }
            if(!Arrays.asList(sigma).contains(Delta.get(i).getSymbol())){
                System.out.println("El símbolo "+ Delta.get(i).getSymbol() + " no existe en el alfabeto");
                return;
            }
            this.Delta.get(Delta.get(i).getInitialState()).add(new Tuple(Delta.get(i).getSymbol(),Delta.get(i).getFinalState()));
        }
        this.cont=0;
    }
    
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
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero1)
              fichero1.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
    
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