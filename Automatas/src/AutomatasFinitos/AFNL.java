/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomatasFinitos;
import Herramientas.TransitionAFNL;
import Herramientas.Tuple;
import LectoresYProcesos.CreadorAutomata;
import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Integer.parseInt;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase es el automata finito no determinista con transiciones lambda
 * en este automata se puede realizar el procesamiento de cadenas sobre el automata ingresado, para saber si esta pertenece o no al lenguaje
 * mientras se muestran los pasos del procesamiento, si así se desea.
 * @author Nathalia
 */
public class AFNL {
     /**
     * El atributo E representa el alfabeto del automata.
     */
    private final char[] E;
     /**
     * El atributo Q representa el conjunto de 
     */
    private final ArrayList<String> Q;
    private final int q0;
    private final ArrayList<Integer> F;
    //public final ArrayList<ArrayList<Tuple>> Delta;
    private final TransitionAFNL T;
    private final char lambda;
    

    public AFNL(char[] E, ArrayList<String> Q, int q0, ArrayList<String> F, TransitionAFNL T) {
        this.E = E;
        this.Q = Q;
        this.q0 = q0;
        this.F = new ArrayList<Integer>();
        for(int i=0;i<F.size();i++)
            this.F.add(Q.indexOf(F.get(i)));
        this.T = T;
        this.lambda = '$';
    }
    
    public AFNL(char[] E, ArrayList<String> Q, int q0, ArrayList<String> F, TransitionAFNL T,char lambda) {
        this.E = E;
        this.Q = Q;
        this.q0 = q0;
        this.F = new ArrayList<Integer>();
        for(int i=0;i<F.size();i++)
            this.F.add(Q.indexOf(F.get(i)));
        this.T = T;
        this.lambda = lambda;
    }
    
    public AFNL(String nombreArchivo) {
        CreadorAutomata.Lecto lec = CreadorAutomata.Lecto.inicio;
        ArrayList<Character> alpha = null;
        ArrayList<String> Estados = null;
        String q0 = null;
        ArrayList<String> F = null;
        //ArrayList<Tuple> Delta = null;
        TransitionAFNL transition = null;
        
        
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
                        Estados = new ArrayList<>();
                        break;
                    case "#initial":
                        if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                        if(Estados==null) throw new Error("primero debe iniciarse los estados");
                        lec = CreadorAutomata.Lecto.estadoinicial;
                        break;
                    case "#accepting":
                        if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                        if(Estados==null) throw new Error("primero debe iniciarse los estados");
                        if(q0==null) throw new Error("primero debe dar el estado inicial");
                        lec = CreadorAutomata.Lecto.estadoFin;
                        F = new ArrayList<>();
                        break;
                    case "#transitions":
                        if(alpha==null) throw new Error("primero debe iniciarse el alfabeto");
                        if(Estados==null) throw new Error("primero debe iniciarse los estados");
                        if(q0==null) throw new Error("primero debe dar el estado inicial");
                        if(F==null) throw new Error("primero debe dar los estados finales");
                        lec = CreadorAutomata.Lecto.transicion;
                        transition = new TransitionAFNL(Estados.size());
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
                                    for (int i = 0; i <= c; i++) {
                                        char d = (char) (a+i);
                                        alpha.add(d);
                                        
                                    }
                                }
                                break;
                            case estados:
                                Estados.add(lin);
                                break;
                            case estadoinicial:
                                if(!Estados.contains(lin))throw new Error("el estado debe pertenecer a los estados del automata");
                                q0=lin;
                                break;
                            case estadoFin:
                                if(!Estados.contains(lin))throw new Error("el estado debe pertenecer a los estados del automata");
                                F.add(lin);
                                break;
                            case transicion:
                                
                                String[] origin = lin.split(":");
                                String estado1 = origin[0];
                                if(!Estados.contains(estado1))throw new Error("el estado del que se parte debe pertenecer a los estados del automata");
                                String[] origin2 = origin[1].split(">");
                                String alpfa = origin2[0];
                                if(!alpha.contains(alpfa.charAt(0)) && !alpfa.equals("$"))throw new Error("el caracter de activacion debe pertenecer al alfabeto");
                                String[] origin3 = origin2[1].split(",");
                                int[] destino = new int[origin3.length];
                                for(int i=0;i<origin3.length;i++){
                                    String estado2 = origin3[i];
                                    if(!Estados.contains(estado2))throw new Error("el estado de llegada debe pertenecer a los estados del automata");
                                    //Delta.add(new Tuple(alpfa.substring(0), parseInt(estado1), parseInt(estado2)));
                                    destino[i] = Estados.indexOf(estado2);
                                }
                                transition.add(alpfa.charAt(0), Estados.indexOf(estado1), destino);
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
        this.E = ad;
        this.Q = Estados;
        this.q0 = Q.indexOf(q0);
        this.F = new ArrayList<Integer>();
        for(int i=0;i<F.size();i++)
            this.F.add(Q.indexOf(F.get(i)));
        this.T = transition;
        this.lambda = '$';
        
    }
    
    //@SuppressWarnings("empty-statement")
    public ArrayList<Integer>  lambdaClausura_unEstado(int estado,ArrayList<Integer> lClausura){
        
        TransitionAFNL Tra= this.T;
        char lambdaTra=this.lambda;
        
        
       Map<Character,ArrayList<Integer>> estadoInterior;
        //estadoInterior = new HashMap<>();
       estadoInterior=Tra.getState(estado);
       for (Map.Entry<Character, ArrayList<Integer>> entry : estadoInterior.entrySet()) {
           
		    if(entry.getKey().equals(lambdaTra) && entry.getValue().isEmpty()==false){
                        lClausura.add(estado);
                        for(int i=0;i<entry.getValue().size();i++){
                            lClausura.add(entry.getValue().get(i));
                            
                            lambdaClausura_unEstado(i,lClausura);
                        }
                        
                    }
		}
       Set<Integer> hashSet = new HashSet<>(lClausura);
        lClausura.clear();
        lClausura.addAll(hashSet);
       

        return lClausura;
    }
    
    public void  ImprimirlambdaClausura_unEstado(int estado,ArrayList<Integer> Clausura){
            
       
        System.out.printf("lambda clausura del estado q"+ estado);
        Clausura.forEach((s) -> {
            System.out.println("q"+s);
        });

        
    }
    
    public ArrayList<Integer>  lambdaClausura_variosEstado(ArrayList<Integer> estados){
        ArrayList<Integer> lClausura= new ArrayList<>(); // arraylist vacio que toca poner para la lambda clausura de un solo estado 
        ArrayList<Integer> lClausura_estados= new ArrayList<>();
        for (int i=0;i<estados.size();i++) {
           ArrayList<Integer> Clauestado=lambdaClausura_unEstado(i, lClausura);
           for(int j =0; j<Clauestado.size();j++){
               lClausura_estados.add(Clauestado.get(j));
           }
        } 
        Set<Integer> hashSet = new HashSet<>(lClausura_estados);
        lClausura_estados.clear();
        lClausura_estados.addAll(hashSet);
        
        return lClausura_estados;
        
        
       
    }
    
    public void  ImprimirlambdaClausura_variosEstado(ArrayList<Integer> estados,ArrayList<Integer> Clausura){
            
       
        System.out.print("lambda clausura de los estados {");
        
        for (Integer s : estados) {
            System.out.print("q"+estados.get(s)+" ");
        }
        System.out.println("} es :");
        
        System.out.print(" }");
        for (Integer s : Clausura) {
            System.out.print("q"+estados.get(s)+" ");
        }
        System.out.print("}");

        
    }
    
    public boolean  ProcesarCadena(String palabra){
        int aceptacion=0;
        TransitionAFNL T=this.T;
        int[] EstadosAceptacion=this.F;
        ArrayList<Integer> EstadosFinales= new ArrayList<>();
        EstadosFinales.add(0);
        for (int i=0;i<palabra.length();i++){
            char letraEvaluada=palabra.charAt(i);
            EstadosFinales=devolverEstadosIteracion(letraEvaluada,EstadosFinales);         
            
        }
//        ArrayList<Integer> fin =EstadosFinales.getFinals();
        for(int j=0; j<EstadosAceptacion.length;j++){
            /*for(int k=0; k<EstadosFinales.size();k++){
                if (EstadosFinales.get(k)==EstadosAceptacion[j]){
                    aceptacion++;
                }
            }*/
           
            if(EstadosFinales.contains(EstadosAceptacion[j])){
                return true;
            }
            
        }
        return aceptacion>0;
        
    }
    
    public ArrayList<Integer> devolverEstadosIteracion(char letra,ArrayList<Integer> estados){
        TransitionAFNL T=this.T;
        char lambdaT=this.lambda;
        
        ArrayList<Integer> estadosFinales=new ArrayList<>();
        for(int i =0;i<estados.size();i++){
            Map<Character,ArrayList<Integer>> estadoInterior;
        
            estadoInterior = T.getState(estados.get(i));
            ArrayList<Integer> estadosFinalesPrev=estadoInterior.get(letra); 
            for(int j=0;j<estadosFinalesPrev.size();j++){
                    estadosFinales.add(estadosFinalesPrev.get(j));
                }
            ArrayList<Integer> estadosFinalesPrevLambda = estadoInterior.get(lambdaT);
            estadosFinalesPrev=devolverEstadosIteracion(letra,estadosFinalesPrevLambda);
            for(int j=0;j<estadosFinalesPrev.size();j++){
                estadosFinales.add(estadosFinalesPrev.get(j));
            }
        }
        Set<Integer> hashSet = new HashSet<>(estadosFinales);
        estadosFinales.clear();
        estadosFinales.addAll(hashSet);
        
        return estadosFinales;
    }
    
    public void ProcesarCadenaConDetalles(String palabra){
        
        TransitionAFNL T=this.T;
        ArrayList<Integer> EstadosFinales= new ArrayList<>();
        EstadosFinales.add(0);
        boolean aceptacion=ProcesarCadena(palabra);
        if(aceptacion== true){
            for (int i=0;i<palabra.length();i++){
                char letraEvaluada=palabra.charAt(i);
                for(int j=0;j<EstadosFinales.size();j++){
                    if(devolverEstadosIteracion(letraEvaluada,EstadosFinales).isEmpty() ==false){
                        System.out.println("q"+EstadosFinales.get(j)+" "+letraEvaluada);
                        EstadosFinales=devolverEstadosIteracion(letraEvaluada,EstadosFinales);
                    }
                }
            
            }
          System.out.println("la cadena fue aceptada");  
        }
        
    }

    /*private RespuestaMult devolverEstadosIteracion(char letra, RespuestaMult EstadosFinales) {
        char lambdaT=this.lambda;
        ArrayList<Integer> estados = EstadosFinales.getFinals();
        for(int i =0;i<estados.size();i++){
            Map<Character,ArrayList<Integer>> estadoInterior;
        
            estadoInterior=T.getState(estados.get(i));
            for (Map.Entry<Character, ArrayList<Integer>> entry : estadoInterior.entrySet()) {           
		    if( entry.getKey().equals(letra)&& entry.getValue().isEmpty()==false){
                      ArrayList<Integer> estadosFinalesPrev=entry.getValue(); 
                      for(int j=0;j<estadosFinalesPrev.size();j++){
                          EstadosFinales.addRuta(estadosFinalesPrev.get(j),i,false);
                          }
                    }else if(entry.getKey().equals(lambdaT)&& entry.getValue().isEmpty()==false){
                        ArrayList<Integer> estadosFinalesPrevLambda=entry.getValue();
                        ArrayList<Integer> estadosFinalesPrev=devolverEstadosIteracion(letra,estadosFinalesPrevLambda);
                      for(int j=0;j<estadosFinalesPrev.size();j++){
                          estadosFinales.add(estadosFinalesPrev.get(j));
                          }
                        
                    }  
                        
            }
        }
        /*Set<Integer> hashSet = new HashSet<>(EstadosFinales);
        EstadosFinales.clear();
        EstadosFinales.addAll(hashSet);
        
        return EstadosFinales;
    }*/
    
}

    
 
    
    
    
    
    


 
