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
import Herramientas.RespuestaMult;
import Herramientas.Tupla;
import ProcesamientoCadenas.ProcesamientoCadenaAFD;
import ProcesamientoCadenas.ProcesamientoCadenaAFNLambda;
/**
 *
 * @author ivonn
 */
public class AFNL {
    private final char[] E;
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
    public ArrayList<Integer>  lambdaClausura_unEstado(int estado){
        ArrayList<Integer> lClausura;
        TransitionAFNL Tra= this.T;
        lClausura=Tra.getState(estado).get(this.lambda);
        lClausura.add(estado);
        if(lClausura.isEmpty()){
            lClausura.add(estado);           
        }else{
            lClausura.add(estado);
            for(int i=0;i<lClausura.size();i++){
            ArrayList<Integer> sublClausura=lambdaClausura_unEstado(lClausura.get(i));
                for(int j=0;j<sublClausura.size();j++){
                    lClausura.add(j);
                    }
                }
               
            }
                        
        
		
       Set<Integer> hashSet = new HashSet<>(lClausura);
        lClausura.clear();
        lClausura.addAll(hashSet);
       

        return lClausura;
    }
    
    public void  ImprimirlambdaClausura_unEstado(int estado){
            
        ArrayList<Integer> Clausura=lambdaClausura_unEstado(estado);
        System.out.printf("lambda clausura del estado q"+ estado);
        Clausura.forEach((s) -> {
            System.out.println("q"+s);
        });

        
    }
    
    public ArrayList<Integer>  lambdaClausura_variosEstado(ArrayList<Integer> estados){
        ArrayList<Integer> lClausura_estados= new ArrayList<>();
        for (int i=0;i<estados.size();i++) {
           ArrayList<Integer> Clauestado=lambdaClausura_unEstado(i);
           for(int j =0; j<Clauestado.size();j++){
               lClausura_estados.add(Clauestado.get(j));
           }
        } 
        Set<Integer> hashSet = new HashSet<>(lClausura_estados);
        lClausura_estados.clear();
        lClausura_estados.addAll(hashSet);
        
        return lClausura_estados;
        
        
       
    }
    
    public void  ImprimirlambdaClausura_variosEstado(ArrayList<Integer> estados){
            
        ArrayList<Integer> Clausura=lambdaClausura_variosEstado(estados);
        System.out.print("lambda clausura de los estados {");
        
        for (Integer s : estados) {
            System.out.print("q"+estados.get(s)+" ");
        }
        System.out.print("} es : ");
        
        System.out.print(" {");
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
    
    
    
    /*public ArrayList<Integer> devolverEstadosIteracion(char letra,ArrayList<Integer> estados){
        TransitionAFNL T=this.T;
        char lambdaT=this.lambda;
        
        ArrayList<Integer> estadosFinales=new ArrayList<>();
        for(int i =0;i<estados.size();i++){
            Map<Character,ArrayList<Integer>> estadoInterior;
        
            estadoInterior=T.getState(estados.get(i));
            for (Map.Entry<Character, ArrayList<Integer>> entry : estadoInterior.entrySet()) {           
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

        
        
    }*/
    
    public RespuestaMult Iteracion(char letra,RespuestaMult caminos){
        
        TransitionAFNL T=this.T;
        ArrayList<Integer> finals=caminos.getFinals();
        for(int i=0; i>finals.size();i++){
            ArrayList<Integer> StepsToAdd=new ArrayList();
            ArrayList<Integer> CaminosLetra=T.getState(finals.get(i)).get(letra);
            ArrayList<Integer> CaminosLambda=T.getState(finals.get(i)).get(this.lambda);
            if(CaminosLetra==null || CaminosLetra.isEmpty() ){
                StepsToAdd.add(null);
            }else{
                for(int j=0;j<CaminosLetra.size();j++){
                    StepsToAdd.add(CaminosLetra.get(i));
                }
            }
            if(CaminosLambda==null || CaminosLambda.isEmpty()){
                StepsToAdd.add(null);
            }else{
                for(int j=0;j<CaminosLetra.size();j++){
                    StepsToAdd.add(CaminosLambda.get(i));
                }
            }
            caminos.addRutas(i,StepsToAdd);
           
        
        }
        return caminos;
    }
    
    public ProcesamientoCadenaAFNLambda procesamiento (String cadena, RespuestaMult respuesta){
        ArrayList<Tupla> tupla = new ArrayList<>();
        
        ArrayList<Integer> resFinals = respuesta.getFinals();
        
        for (int i = 0; i < resFinals.size(); i++) {
            ArrayList<Integer> caminoespecifico = respuesta.getCamino(i);
            String camino =  "";
            for (int j = 0; j < caminoespecifico.size(); j++) {
                ArrayList<Integer> rutaIndex = lambdaClausura_unEstado(caminoespecifico.get(i));
                camino+="[{";
                for (int k = 0; k < rutaIndex.size(); k++) {
                    camino += Q.get(rutaIndex.get(k)) + ",";
                }
                camino = camino.substring(0,camino.length()-1) + "}, " + cadena.substring(j) + "] -> ";
            }
            try{
                if(resFinals.get(i) == null){
                    throw new NullPointerException();
                }
                if(F.contains(resFinals.get(i))){
                    tupla.add(new Tupla(camino + " Aceptado", Tupla.Case_Aceptado));
                }else{
                    tupla.add(new Tupla(camino + " Rechazado", Tupla.Case_Rechazado));
                }
            }catch(NullPointerException e){
                tupla.add(new Tupla(camino + " Abortado", Tupla.Case_Abortado));
            }
        }
        
        return new ProcesamientoCadenaAFNLambda(cadena, tupla);
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
    
 
    
    
    
    
    


 
