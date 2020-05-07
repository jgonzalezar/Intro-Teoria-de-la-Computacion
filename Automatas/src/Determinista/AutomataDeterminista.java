package Determinista;

import java.util.ArrayList;
import java.util.Arrays;
import Herramientas.Tuple;

public class AutomataDeterminista {
	String alfabeto;
	ArrayList<ArrayList<Tuple>> automata;
	ArrayList<Integer> acceptance;

	public AutomataDeterminista(String alfabeto) {
		this.alfabeto = alfabeto;
		this.automata = new ArrayList<ArrayList<Tuple>>();
		this.acceptance = new ArrayList<Integer>();
	}

	public void addConnections(ArrayList<Tuple> calledBy) {
		for(int i=0;i<calledBy.size();i++) {
			while(calledBy.get(i).getFinalState()>=this.automata.size()) {
				this.automata.add(new ArrayList<Tuple>());
			}
			this.automata.get(calledBy.get(i).getInitialState()).add(new Tuple(calledBy.get(i).getSymbol(),calledBy.get(i).getFinalState()));
		}		
		return;
	}
	
	public void addAcceptance(ArrayList<Integer> states) {
		this.acceptance = states;
	}
	
	public Integer getNumberStates() {
		return this.automata.size();
	}
	
	public void process(String word) {
		if(word.length()==0) {
			if(Arrays.asList(this.acceptance).contains(0)) {
				System.out.println("La cadena vacia es aceptada");
			}else {
				System.out.println("La cadena vacia es rechazada");
			}
			return;
		}
		
		int state=0;
		boolean pass;
		for(int i=0; i<word.length();i++) {
			pass = true;
			ArrayList<Tuple> automataState = this.automata.get(state);
			for(int j=0; j<automataState.size();j++) {
				if(automataState.get(j).getSymbol().equals(word.substring(i,i+1))) {
					state = automataState.get(j).getFinalState();
					pass = false;
					break;
				}
			}
			if(pass) {
				System.out.println("No se acepta la cadena");
				return;
			}
		}
		System.out.println("La cadena resulta en el estado q"+state);
		
		if(Arrays.asList(this.acceptance).contains(state)) {
			System.out.println("La cadena es aceptada");
		}else {
			System.out.println("La cadena es rechazada");
		}
		return;
	}
	
	public void printAutomata() {
		System.out.println("El automata determinista ingresado es:");
		for(int i=0;i<this.automata.size();i++) {
			System.out.print("q"+i + ": ");
			for(int j=0;j<this.automata.get(i).size();j++) {
				System.out.print(automata.get(i).get(j).getSymbol()+",q" + automata.get(i).get(j).getFinalState() + " ");
			}
			System.out.println();
		}
		System.out.println();
		if(this.acceptance.size()>0) {
			System.out.println("Y sus estados de aceptación son:");
			for(int i=0;i<this.acceptance.size();i++) {
				System.out.print("q"+acceptance.get(i) + " ");
			}
		}else {
			System.out.println("Aún no tiene estados de aceptación");
		}
		System.out.println();
		System.out.println();
	}
	
}
