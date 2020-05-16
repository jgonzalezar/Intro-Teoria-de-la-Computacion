package AutomatasFinitos;

import Herramientas.Respuesta;
import Herramientas.Transition;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Esta clase es el automata finito determinista
 * en este automata se pueden realizar procesamientos de palabras sobre si mismo, confirmando si pertenece o no al lenguaje, e incluso se puede recibir una respuesta con pasos incluidos
 * 
 * @author equipo los Javas
 * @version 1.2
 */
public class AFD {
    /**
     * El atributo Sigma representa el alfabeto del automata.
     * Esta dado por la clase String, y cada una de los caracteres de Sigma es uno de los simbolos del alfabeto.     * 
     */
    public final String Sigma;
    /**
     * El atributo Q representa la cantidad total de estados dentro del automata.
     */
    public final int Q; 
    /**
     * El atributo q0 representa el estado inicial del autómata.
     */
    public final int q0;
    /**
     * El atributo F representa los estados aceptados del autómata. 
     */
    public final ArrayList<Integer> F;
    /**
     * El atributo Delta representa la función de transición del autómata.
     */
    public final Transition Delta;
    
    /**
     * Constructor 
     * @param Sigma
     * @param Q
     * @param q0
     * @param F
     * @param Delta 
     */
    public AFD(String Sigma, int Q, int q0, ArrayList<Integer> F, Transition Delta) {
        this.Sigma = Sigma;
        this.Q = Q;
        this.q0 = q0;
        this.F = F;
        this.Delta = Delta;
    }

    public AFD(String Sigma, int q0, ArrayList<Integer> F, Transition Delta) {
        this.Sigma = Sigma;
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
        /*if(!Sigma.contains(u)){
            //System.err.println("Sigmal simbolo "+u+"no pertenece a el Alfabeto");
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
            System.err.println("Sigmal simbolo \""+a+"\" no pertenece a el Alfabeto");
        }*/
        return q;
    }
}
