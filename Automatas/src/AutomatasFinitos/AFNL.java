/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutomatasFinitos;
import Herramientas.TransitionAFNL;
import Herramientas.Respuesta;
import Herramientas.Transition;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 *
 * @author ivonn
 */
public class AFNL {
    private final char[] E;
    private final int Q;
    private final int q0;
    private final int[] F;
    private final TransitionAFNL T;
    private final String lambda;
    

    public AFNL(char[] E, int Q, int q0, int[] F, TransitionAFNL T) {
        this.E = E;
        this.Q = Q;
        this.q0 = q0;
        this.F = F;
        this.T = T;
        this.lambda = "L";
    }
    
    public AFNL(char[] E, int Q, int q0, int[] F, TransitionAFNL T,String lambda) {
        this.E = E;
        this.Q = Q;
        this.q0 = q0;
        this.F = F;
        this.T = T;
        this.lambda = lambda;
    }
    
    //@SuppressWarnings("empty-statement")
    public ArrayList<Integer>  lambdaClausura_unEstado(int estado,ArrayList<Integer> lClausura){
        
        TransitionAFNL Tra= this.T;
        String lambdaTra=this.lambda;
        
        
       Map<String,ArrayList<Integer>> estadoInterior;
        //estadoInterior = new HashMap<>();
       estadoInterior=Tra.getState(estado);
       for (Map.Entry<String, ArrayList<Integer>> entry : estadoInterior.entrySet()) {
           
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
        for (Integer s : Clausura) {
            System.out.println("q"+s);
        }

        
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
        for(int j=0; j<EstadosAceptacion.length;j++){
            for(int k=0; k<EstadosFinales.size();k++){
                if (EstadosFinales.get(k)==EstadosAceptacion[j]){
                    aceptacion++;
                }
            }
            
        }
        return aceptacion>0;
        
    }
    
    public ArrayList<Integer> devolverEstadosIteracion(char letra,ArrayList<Integer> estados){
        TransitionAFNL T=this.T;
        String lambdaT=this.lambda;
        ArrayList<Integer> estadosFinales=new ArrayList<>();
        for(int i =0;i<estados.size();i++){
            Map<String,ArrayList<Integer>> estadoInterior;
        
            estadoInterior=T.getState(estados.get(i));
            for (Map.Entry<String, ArrayList<Integer>> entry : estadoInterior.entrySet()) {           
		    if( entry.getKey().equals(letra)&& entry.getValue().isEmpty()==false){
                      ArrayList<Integer> estadosFinalesPrev=entry.getValue(); 
                      for(int j=0;j<estadosFinalesPrev.size();j++){
                          estadosFinales.add(estadosFinalesPrev.get(j));
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
        Set<Integer> hashSet = new HashSet<>(estadosFinales);
        estadosFinales.clear();
        estadosFinales.addAll(hashSet);
        
        return estadosFinales;

        
        
    }
    
    public void ProcesarCadenaConDetalles(){
    }
    
}
 
    
    
    
    
    


 
