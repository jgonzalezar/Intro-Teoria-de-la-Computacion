package AutomatasFinitos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import AutomatasFinitos.AFD;
import Herramientas.Tuple;

public class AFN {
	String alfabeto, word;
	Integer[] print;
	ArrayList<Tuple> newStates;
	ArrayList<ArrayList<Tuple>> automata,automataDeterminista;
	ArrayList<Integer> usefulStates, acceptance, detAcceptance;
	int cont;

	public AFN(String alfabeto) {
		this.alfabeto = alfabeto;
		this.automata = new ArrayList<ArrayList<Tuple>>();
		this.cont=0;
		this.usefulStates = new ArrayList<Integer>();
		this.acceptance = new ArrayList<Integer>();
		this.detAcceptance = new ArrayList<Integer>();
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
	
	private int movement(int state, int letter) {
		boolean pass=true;
		ArrayList<Tuple> automataState = this.automata.get(state);
		
		for(int i=0; i<automataState.size();i++) {
			if(automataState.get(i).getSymbol().equals(this.word.substring(letter,letter+1))) {
				state = automataState.get(i).getFinalState();
				this.print[letter]=state;
				pass=false;
				if(letter<word.length()-1) {
					state = movement(state,letter+1);
				}if(letter==word.length()-1){
					System.out.print("q0 ");
					for(int j=0;j<this.word.length();j++) {
						System.out.print("q"+this.print[j]+" ");
					}
					if(Arrays.asList(this.acceptance).contains(state)) {
						System.out.println(" La cadena es aceptada");
					}else {
						System.out.println(" La cadena es rechazada");
					}
				}
			}
		}
		if(pass) {
			System.out.print("Cadena procesada incorrectamente: q0 ");
			for(int j=0;j<letter;j++) {
				System.out.print("q"+this.print[j]+" ");
			}
			System.out.println();
		}
		return state;
	}
	
	public void process(String word) {
		this.word=word;
		if(word.length()==0) {
			if(Arrays.asList(this.acceptance).contains(0)) {
				System.out.println("La cadena vacia es aceptada");
			}else {
				System.out.println("La cadena vacia es rechazada");
			}
			return;
		}
		this.print = new Integer[word.length()];
		System.out.println("Procesamientos: ");
		movement(0, 0);
		return;
	}
	
	public AFD toDeterministic() {
		ArrayList<Integer> states = new ArrayList<Integer>();
		this.newStates = new ArrayList<Tuple>();
		int value;
		String statesString;
		ArrayList<Tuple> deterministicStates;
		this.automataDeterminista = new ArrayList<ArrayList<Tuple>>();
		
		for(int i=0;i<this.acceptance.size();i++) {
			this.detAcceptance.add(this.acceptance.get(i));
		}
		
		for(int i=0;i<this.automata.size();i++) {
			deterministicStates= new ArrayList<Tuple>();
			for(int j=0;j<this.alfabeto.length();j++) {
				states.clear();
				for(int k=0;k<this.automata.get(i).size();k++) {
					if(this.automata.get(i).get(k).getSymbol().equals(this.alfabeto.substring(j,j+1))) {
						states.add(this.automata.get(i).get(k).getFinalState());
					}
				}
				Collections.sort(states);
				if(states.size()>0) {
					statesString=states.get(0).toString();
					for(int a=1;a<states.size();a++) {
						statesString = statesString+ " " +states.get(a).toString() ;
					}
					if(states.size()>1) {
						value = contains(statesString);
						if(value>=0) {
							deterministicStates.add(new Tuple(this.alfabeto.substring(j,j+1),value));
						}else{
							newStates.add(new Tuple(statesString,this.automata.size()+this.cont));
							deterministicStates.add(new Tuple(this.alfabeto.substring(j,j+1),this.automata.size()+this.cont));
							this.cont++;
						}
					}else{
						deterministicStates.add(new Tuple(this.alfabeto.substring(j,j+1),states.get(0)));
					}
				}
			}
			this.automataDeterminista.add(deterministicStates);
		}
		addNewStates();
		
		usefulStates.add(0);
		identifyUsefulStates(0);
		
		Collections.sort(usefulStates);
		Set<Integer> hashSet = new HashSet<Integer>(usefulStates);
		usefulStates.clear();
		usefulStates.addAll(hashSet);
		
		getAcceptance();
		reduceAutomata();
		printDeterministic();
		printStateEquivalence();
		
		AFD newDet = new AFD(this.alfabeto);
		ArrayList<Tuple> connections = new ArrayList<Tuple>();
		for(int i=0;i<this.automataDeterminista.size();i++) {
			for(int j=0;j<this.automataDeterminista.get(i).size();j++) {
				connections.add(new Tuple(this.automataDeterminista.get(i).get(j).getSymbol(),i,this.automataDeterminista.get(i).get(j).getFinalState()));
			}
		}
		newDet.addConnections(connections);
		newDet.addAcceptance(this.detAcceptance);
		return newDet;
	}
	
	private void identifyUsefulStates(int state) {
		
		ArrayList<Tuple> automataState = this.automataDeterminista.get(state);
		
		for(int j=0; j<automataState.size();j++) {
			for(int i=0;i<this.alfabeto.length();i++) {
				if(automataState.get(j).getFinalState() != state && automataState.get(j).getSymbol().equals(this.alfabeto.substring(i,i+1))) {
					if(!usefulStates.contains(automataState.get(j).getFinalState())) {
						usefulStates.add(automataState.get(j).getFinalState());
						identifyUsefulStates(automataState.get(j).getFinalState());
					}
				}else if(automataState.get(j).getFinalState() == state && automataState.get(j).getSymbol().equals(this.alfabeto.substring(i,i+1)) && !usefulStates.contains(state)) {
					usefulStates.add(state);
				}
			}
		}
	}
	
	private void reduceAutomata() {
		int cont=0;
		for(int i=0;i<this.automataDeterminista.size();i++) {
			if(!this.usefulStates.contains(i)) {
				if(this.detAcceptance.contains(i)) {
					for(int j=0;j<this.detAcceptance.size();j++) {
						if(this.detAcceptance.get(j)==i) {
							this.detAcceptance.remove(j);
							break;
						}
					}
				}
				cont++;
			}else if(cont>0) {
				for(int j=i;j<this.automataDeterminista.size();j++) {
					this.automataDeterminista.set(j-cont,this.automataDeterminista.get(j));
					for(int k=0;k<this.newStates.size();k++) {
						if(this.newStates.get(k).getFinalState() == j) {
							this.newStates.set(k, new Tuple(this.newStates.get(k).getSymbol(),j-cont));
						}
					}
					for(int k=0;k<this.detAcceptance.size();k++) {
						if(this.detAcceptance.get(k)==j) {
							this.detAcceptance.set(k,j-cont);
						}
					}
				}
				for(int j=0;j<cont;j++) {
					this.automataDeterminista.remove(this.automataDeterminista.size()-1);
				}
				for(int j=0;j<this.automataDeterminista.size();j++){
					for(int k=0;k<this.automataDeterminista.get(j).size();k++) {
						if(this.automataDeterminista.get(j).get(k).getFinalState()>=i) {
							this.automataDeterminista.get(j).set(k,new Tuple(this.automataDeterminista.get(j).get(k).getSymbol(),this.automataDeterminista.get(j).get(k).getFinalState()-cont));
						}
					}
				}
				cont=0;
			}
		}
	}
	
	private void getAcceptance() {
		ArrayList<Integer> auxArray;
		for(int i=0;i<this.newStates.size();i++) {
			auxArray = stringToValues(this.newStates.get(i).getSymbol());
			for(int j=0;j<auxArray.size();j++) {
				if(this.detAcceptance.contains(auxArray.get(j))) {
					this.detAcceptance.add(this.newStates.get(i).getFinalState());
					break;
				}
			}
			System.out.println();
		}
	}
	
	private void printStateEquivalence() {
		ArrayList<Integer> auxArray;
		System.out.println("Los estados a�adidos representan los conjuntos de estados:");
		for(int i=0;i<this.newStates.size();i++) {
			System.out.print("q"+this.newStates.get(i).getFinalState()+": ");
			auxArray = stringToValues(this.newStates.get(i).getSymbol());
			for(int j=0;j<auxArray.size();j++) {
				System.out.print("q"+auxArray.get(j)+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	private void printDeterministic() {
		System.out.println("El automata determinista resultante es:");
		for(int i=0;i<this.automataDeterminista.size();i++) {
				System.out.print("q"+i + ": ");
				for(int j=0;j<this.automataDeterminista.get(i).size();j++) {
					System.out.print(this.automataDeterminista.get(i).get(j).getSymbol()+",q" + this.automataDeterminista.get(i).get(j).getFinalState() + " ");
				}
				System.out.println();
		}
		System.out.println();
		if(this.detAcceptance.size()>0) {
			System.out.println("Y sus estados de aceptaci�n son:");
			for(int i=0;i<this.detAcceptance.size();i++) {
				System.out.print("q"+detAcceptance.get(i) + " ");
			}
		}else{
			System.out.println("El automata no determinista no tenia estados de aceptaci�n");
		}
		System.out.println();
		System.out.println();
	}
	
	private int contains(String a) {
		for(int i=0;i<this.newStates.size();i++) {
			if(this.newStates.get(i).getSymbol().equals(a)) {
				return this.newStates.get(i).getFinalState();
			}
		}
		return -1;
	}
	
	private String search(int a) {
		for(int i=0;i<this.newStates.size();i++) {
			if(this.newStates.get(i).getFinalState()== a) {
				return this.newStates.get(i).getSymbol();
			}
		}
		return "";
	}
	
	private ArrayList<Integer> stringToValues(String cadena) {
		int value = 0;
		ArrayList<Integer> combinationOf = new ArrayList<Integer>();
		for(int k=0;k<cadena.length();k++){
			if(k==cadena.length()-1) {
				combinationOf.add(Integer.parseInt(cadena.substring(k-value,k+1)));
			}else if(cadena.substring(k,k+1).equals(" ")) {
				combinationOf.add(Integer.parseInt(cadena.substring(k-value,k)));
				value = 0;
			}else{
				value++;
			}
		}
		return combinationOf;
	}
	
	private void addNewStates() {
		ArrayList<Tuple> symbolStates;
		ArrayList<Integer> combinationOf, states = new ArrayList<Integer>(), aux;
		String statesString;
		int value, stateNumber;
		
		for(int i=0;i<this.newStates.size();i++) {
			
			symbolStates= new ArrayList<Tuple>();
			combinationOf = stringToValues(this.newStates.get(i).getSymbol());
			
			for(int l=0;l<this.alfabeto.length();l++) {
				states.clear();
				for(int j=0;j<combinationOf.size();j++){
					for(int k=0;k<this.automataDeterminista.get(combinationOf.get(j)).size();k++) {
						if(this.automataDeterminista.get(combinationOf.get(j)).get(k).getSymbol().equals(this.alfabeto.substring(l,l+1))) {
							if(this.automataDeterminista.get(combinationOf.get(j)).get(k).getFinalState()<this.automata.size()) {
								states.add(this.automataDeterminista.get(combinationOf.get(j)).get(k).getFinalState());
							}else{
								aux = stringToValues(search(this.automataDeterminista.get(combinationOf.get(j)).get(k).getFinalState()));
								for(int m=0;m<aux.size();m++) {
									states.add(aux.get(m));
								}
							}
						}
					}
				}
				Collections.sort(states);
				Set<Integer> hashSet = new HashSet<Integer>(states);
		        states.clear();
		        states.addAll(hashSet);
		        
		        if(states.size()>0) {
		        	statesString=states.get(0).toString();
					for(int a=1;a<states.size();a++) {
						statesString = statesString+ " " +states.get(a).toString() ;
					}
					if(states.size()>1) {
						value = contains(statesString);
						if(value>=0) {
							symbolStates.add(new Tuple(this.alfabeto.substring(l,l+1),value));
						}else{
							newStates.add(new Tuple(statesString,this.automata.size()+this.cont));
							symbolStates.add(new Tuple(this.alfabeto.substring(l,l+1),this.automata.size()+this.cont));
							this.cont++;
						}
					}else{
						symbolStates.add(new Tuple(this.alfabeto.substring(l,l+1),states.get(0)));
					}
				}
			}
			this.automataDeterminista.add(symbolStates);
		}
	}
	
	public void printAutomata() {
		System.out.println("El automata no determinista ingresado es:");
		for(int i=0;i<this.automata.size();i++) {
			System.out.print("q"+i + ": ");
			for(int j=0;j<this.automata.get(i).size();j++) {
				System.out.print(this.automata.get(i).get(j).getSymbol()+",q" + this.automata.get(i).get(j).getFinalState() + " ");
			}
			System.out.println();
		}
		System.out.println();
		if(this.acceptance.size()>0) {
			System.out.println("Y sus estados de aceptaci�n son:");
			for(int i=0;i<this.acceptance.size();i++) {
				System.out.print("q"+acceptance.get(i) + " ");
			}
		}else {
			System.out.println("A�n no tiene estados de aceptaci�n");
		}
		System.out.println();
		System.out.println();
	}
	
}
