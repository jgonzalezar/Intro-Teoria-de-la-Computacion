import java.util.ArrayList;
import AutomatasFinitos.AFD;
import Herramientas.Tuple;
import AutomatasFinitos.AFN;

import java.util.Scanner;


public class main {

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		
		//Automata Determinista----------------------------------------------------------------------
		
//		AFD automataDet = new AFD("ab");
//		ArrayList<Tuple> connections = new ArrayList<Tuple>();
//
//		connections.add(new Tuple("a",0,1));
//		connections.add(new Tuple("b",0,2));
//		connections.add(new Tuple("b",1,4));
//		connections.add(new Tuple("b",2,2));
//		connections.add(new Tuple("a",2,3));
//		connections.add(new Tuple("b",3,4));
//		connections.add(new Tuple("a",3,5));
//		connections.add(new Tuple("a",4,5));
//		connections.add(new Tuple("a",5,5));
//		
//		automataDet.addConnections(connections);
//		
//		ArrayList<Integer> acceptance = new ArrayList<Integer>();
//		acceptance.add(5);
//		automataDet.addAcceptance(acceptance);
//		
//		automataDet.printAutomata();
//	
//		System.out.println("Numero de estados: " + automataDet.getNumberStates().toString());
//		System.out.println("Ingrese la palabra a procesar:");
//		String word=entrada.nextLine();
//		automataDet.process(word);
		
		//Automata No Determinista-------------------------------------------------------------------
		
		AFN automataNoDet = new AFN("ab");
		ArrayList<Tuple> connections = new ArrayList<Tuple>();

//		connections.add(new Tuple("0",0,1));
//		connections.add(new Tuple("0",1,3));
//		connections.add(new Tuple("1",1,2));
//		connections.add(new Tuple("0",2,1));
//		connections.add(new Tuple("1",3,2));
		
//		connections.add(new Tuple("b",0,0));
//		connections.add(new Tuple("b",0,1));
//		connections.add(new Tuple("a",0,2));
//		connections.add(new Tuple("b",2,4));
//		connections.add(new Tuple("a",1,4));
//		connections.add(new Tuple("a",4,2));
//		connections.add(new Tuple("b",4,1));
		
//		connections.add(new Tuple("a",0,0));
//		connections.add(new Tuple("a",0,1));
//		connections.add(new Tuple("b",0,1));
//		connections.add(new Tuple("a",1,0));
//		connections.add(new Tuple("b",1,0));
		
		//2.5 - (1) I)
//		connections.add(new Tuple("b",0,1));
//		connections.add(new Tuple("b",0,2));
//		connections.add(new Tuple("a",1,0));
//		connections.add(new Tuple("a",2,3));
//		connections.add(new Tuple("b",3,0));
		
		//2.5 - (1) II)		
		connections.add(new Tuple("b",0,1));
		connections.add(new Tuple("b",0,2));
		connections.add(new Tuple("a",1,0));
		connections.add(new Tuple("a",1,1));
		connections.add(new Tuple("a",2,3));
		connections.add(new Tuple("b",3,0));
		
		//2.5 - (1) III)		
//		connections.add(new Tuple("b",0,0));
//		connections.add(new Tuple("b",0,1));
//		connections.add(new Tuple("a",1,1));
//		connections.add(new Tuple("a",1,2));
//		connections.add(new Tuple("b",2,1));
		
		//2.5 - (1) IV)		
//		connections.add(new Tuple("a",0,0));
//		connections.add(new Tuple("a",0,1));
//		connections.add(new Tuple("b",1,2));
//		connections.add(new Tuple("a",2,1));
//		connections.add(new Tuple("a",2,0));
		
		
		//2.5 - (2)
//		connections.add(new Tuple("0",0,0));
//		connections.add(new Tuple("1",0,0));
//		connections.add(new Tuple("1",0,1));
//		connections.add(new Tuple("0",1,2));
//		connections.add(new Tuple("1",1,2));
//		connections.add(new Tuple("0",2,3));
//		connections.add(new Tuple("1",2,3));
		
		ArrayList<Integer> acceptance = new ArrayList<Integer>();
		acceptance.add(0);
		acceptance.add(3);
		automataNoDet.addAcceptance(acceptance);
		
		automataNoDet.addConnections(connections);
		automataNoDet.printAutomata();
		
		AFD automataDet = automataNoDet.toDeterministic();
		
//		System.out.println("Ingrese la palabra a procesar:");
//		String word=entrada.nextLine();
//		automataNoDet.process(word);
	}

}
