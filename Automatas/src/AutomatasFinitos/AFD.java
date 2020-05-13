package AutomatasFinitos;

import Herramientas.Respuesta;
import Herramientas.Transition;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * esta clase es el automata finito determinista
 * en este automata se pueden realizar procesamientos de palabras sobre si mismo, confirmando si pertenece o no al lenguaje, e incluso se puede recibir una respuesta con pasos incluidos
 * 
 * @author equipo los Javas
 * @version 1.2
 */
public class AFD {
    /**
     * El atributo Sigma representa el alfabeto del automata,
     * esta dado por la clase String, y cada una de los caracteres de Sigma es uno de los simbolos del alfabeto.
     * 
     */
    public final String Sigma;
    /**
     * El atributo Q representa la cantidad total de estados dentro del automata.
     */
    public final int Q; 
    /**
     * 
     */
    public final int q0;
    public final ArrayList<Integer> F;
    public final Transition Delta;
    
    /**
     * Constructor 
     * @param E
     * @param Q
     * @param q0
     * @param F
     * @param Delta 
     */
    public AFD(String E, int Q, int q0, ArrayList<Integer> F, Transition Delta) {
        this.E = E;
        this.Q = Q;
        this.q0 = q0;
        this.F = F;
        this.Delta = Delta;
    }

    public AFD(String E, int q0, ArrayList<Integer> F, Transition Delta) {
        this.E = E;
        
        
        this.q0 = q0;
        this.F = F;
        this.Delta = Delta;
        int f= F.get(0);
        for (int i = 0; i < F.size(); i++) {
            if(f<F.get(i)){
                f=F.get(i);
            }
        }
        if(f>=this.Delta.size()){
            this.Q =f;
        }else{
            this.Q = Delta.size();
        }
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
	
	/**
         * 
         * @param word
         * @return 
         */
    public boolean procesarCadena(String word){
        if(word==null||word.length()==0){            
            return F.contains(q0);
        }
        return prosCaden(word).aceptado;
    }
    
    public boolean procesarCadena(char[] word){
        return procesarCadena(Arrays.toString(word));
    }
    public boolean procesarCadenaConDetalles(char[] word){
        return procesarCadenaConDetalles(Arrays.toString(word));
    }
    
    public Respuesta prosCaden(String word){
        return Finish(Delta(word));
    }
    
    public boolean procesarCadenaConDetalles(String word){
        Respuesta fin =prosCaden(word);
        System.out.println(fin.pasos());
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
                String pas =res.pasos();
                String res2= listaCadenas[i]+"\t"+ pas.substring(0, pas.length()-1)+"\t"+res.aceptado;
                pw1.println(res2);
                if(imprimirPantalla) System.out.println(res2);
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
            return Delta(f,word.substring(0,1));
        }
        Respuesta det = Delta(word.substring(0, word.length()-1));
        return Delta(det,word.substring(word.length()-1, word.length()));
    }
    
    private Respuesta Delta(Respuesta i, String u) {
        /*if(!E.contains(u)){
            //System.err.println("El simbolo "+u+"no pertenece a el Alfabeto");
            int as = -u.charAt(0);
            return as;
        }*/
        if(i.pasos.get(i.pasos.size()-1)>=0){
            int tas = Delta.cambio(i.pasos.get(i.pasos.size()-1),u);
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
            System.err.println("El simbolo \""+a+"\" no pertenece a el Alfabeto");
        }*/
        return q;
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
