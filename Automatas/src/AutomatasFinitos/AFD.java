package AutomatasFinitos;

import Herramientas.Respuesta;
import Herramientas.Transition;
import java.util.ArrayList;
import java.util.Arrays;
import Herramientas.Tuple;

/**
 * 
 * @author equipo los Javas
 */
public class AFD {
    public final String E;
    public final int Q;
    public final int q0;
    public final ArrayList<Integer> F;
    public final Transition Delta;

    public AFD(String E, int Q, int q0, ArrayList<Integer> F, Transition Delta) {
        this.E = E;
        this.Q = Q;
        this.q0 = q0;
        this.F = F;
        this.Delta = Delta;
    }

    public AFD(String E, int q0, ArrayList<Integer> F, Transition Delta) {
        this.E = E;
        this.Q = Delta.size();
        this.q0 = q0;
        this.F = F;
        this.Delta = Delta;
    }

    /*

	public void addConnections(ArrayList<Tuple> calledBy) {
		for(int i=0;i<calledBy.size();i++) {
			while(calledBy.get(i).getFinalState()>=this.Delta.size()) {
				this.Delta.add(new ArrayList<Tuple>());
			}
			this.Delta.get(calledBy.get(i).getInitialState()).add(new Tuple(calledBy.get(i).getSymbol(),calledBy.get(i).getFinalState()));
		}		
		return;
	}*/
	
	
    public boolean procesarCadena(String word){
        if(word==null||word.length()==0){            
            return F.contains(q0);
        }
        return Finish(Delta(word,false),false).aceptado;
    }
    
    public boolean procesarCadena(char[] word){
        return procesarCadena(Arrays.toString(word));
    }
    public Respuesta procesarCadenaConDetalles(char[] word){
        return procesarCadenaConDetalles(Arrays.toString(word));
    }
    
    public Respuesta procesarCadenaConDetalles(String word){
        if(word==null||word.length()==0){            
            return F.contains(q0);
        }
        return Finish(Delta(word,true),true);
    }

    private int Delta(String word,boolean deta) {
        if(word.length()==1){
            return Delta(q0,word.substring(0,1),deta);
        }
        int det = Delta(word.substring(0, word.length()-1),deta);
        return Delta(det,word.substring(word.length()-1, word.length()),deta);
    }
    
    private int Delta(int i, String u,boolean deta) {
        if(!E.contains(u)){
            //System.err.println("El simbolo "+u+"no pertenece a el Alfabeto");
            int as = -u.charAt(0);
            return as;
        }
        if(i<0){
            int as = -1;
            return as;
        }
        if(deta)System.out.print("q"+i+" ");
        int tas = Delta.cambio(i,u);
        return tas;
    }

    private Respuesta Finish(Respuesta q,boolean deta) {
        if(deta)System.out.println("q"+q+" ");
        if(F.contains(q)){        
            return true;
        }
        if(q<-1){
            char a = (char)-q;
            System.out.println(a);
            System.err.println("El simbolo \""+a+"\" no pertenece a el Alfabeto");
        }
        return false;
    }
    
    
	
   /* public void printAutomata() {
        System.out.println("El Delta determinista ingresado es:");
	for(int i=0;i<this.Delta.size();i++) {
            System.out.print("q"+i + ": ");
		for(int j=0;j<this.Delta.get(i).size();j++) {
                    System.out.print(Delta.get(i).get(j).getSymbol()+",q" + Delta.get(i).get(j).getFinalState() + " ");
		}
            System.out.println();
	}
	System.out.println();
        if(this.F.size()>0) {
            System.out.println("Y sus estados de aceptaci�n son:");
            for(int i=0;i<this.F.size();i++) {
		System.out.print("q"+F.get(i) + " ");
            }
		}else {
			System.out.println("A�n no tiene estados de aceptaci�n");
		}
		System.out.println();
		System.out.println();
	}
	*/
}
