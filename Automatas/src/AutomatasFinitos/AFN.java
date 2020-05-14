package AutomatasFinitos;

import Herramientas.NDRespuesta;
import java.util.ArrayList;
import java.util.Arrays;
import Herramientas.Tuple;

public class AFN {
    public final String sigma; 
    public final int Q;
    public final int q0;
    public final ArrayList<Integer> F;
    public final ArrayList<ArrayList<Tuple>> Delta;
    
    int cont;
    String cadena;
    Integer[] print;
    NDRespuesta respuesta;

    public AFN(String sigma, int Q, int q0, ArrayList<Integer> F, ArrayList<Tuple> Delta){
        this.sigma = sigma;
        this.Q = Q;
        this.q0 = q0;
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
            this.Delta.get(Delta.get(i).getInitialState()).add(new Tuple(Delta.get(i).getSymbol(),Delta.get(i).getFinalState()));
        }
        this.cont=0;
    }
    
    public boolean procesarCadena(String cadena){
        if(cadena.length()==0) {
            if(this.F.contains(0)) {
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
            if(this.F.contains(0)) {
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
            if(this.F.contains(0)) {
		return 1;
            }
            return 0;
        }
        this.cadena=cadena;
        this.print = new Integer[cadena.length()];
        respuesta = new NDRespuesta(cadena);
        computarTodosLosProcesamientos(q0,0);
        
        System.out.println("Aceptadas "+respuesta.getAccepted());
        System.out.println("Rechazadas "+respuesta.getRejected());
        System.out.println("Abortadas "+respuesta.getAborted());
        return 0;
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
        this.cadena=cadena;
        this.print = new Integer[cadena.length()];
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