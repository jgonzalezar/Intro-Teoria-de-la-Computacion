package ProcesamientoCadenas;

import Herramientas.NDRespuesta;
import java.util.ArrayList;

/**
 *
 * @author jdacostabe
 */
public class ProcesamientoCadenaAFN {
    String cadena;
    boolean isAccepted;
    NDRespuesta procesamientos;
    
    public ProcesamientoCadenaAFN(String cadena, NDRespuesta procesamientos){
        this.cadena = cadena;
        this.isAccepted = procesamientos.isAccepted();
        this.procesamientos = procesamientos;
    }
    
    public void procesarCadena(){
        System.out.println("Procesar cadena: "+ isAccepted+"\n");
    }
    
    public void procesarCadenaConDetalles(){
        System.out.println("Procesar cadena con detalles:");
        if(procesamientos.isAccepted()){
            System.out.print(procesamientos.getAccepted().get(0)+"\tCadena aceptada");
        }else if(procesamientos.getRejected().size()>0){
            System.out.print(procesamientos.getRejected().get(0)+"\tCadena rechazada");
        }else if(procesamientos.getAborted().size()>0){
            System.out.print(procesamientos.getAborted().get(0) + "\tCadena abortada");
        }
        System.out.println("\n");
    }
    
    public void computarTodosLosProcesamientos(){
        System.out.println("Computar todos los procesamientos:");
        String res="Aceptadas:\n  ";
        if(procesamientos.getAccepted().isEmpty()){
            res+="No hay procesamientos aceptados para esta cadena\n";
        }
        for(int i=0;i<procesamientos.getAccepted().size();i++){
            res+=procesamientos.getAccepted().get(i)+"\n";
        }
        res+="Rechazadas:\n  ";
        if(procesamientos.getRejected().isEmpty()){
            res+="No hay procesamientos rechazados para esta cadena\n";
        }
        for(int i=0;i<procesamientos.getRejected().size();i++){
            res+=procesamientos.getRejected().get(i)+"\n";
        }
        res+="Abortadas:\n  ";
        if(procesamientos.getAborted().isEmpty()){
            res+="No hay procesamientos abortados para esta cadena\n";
        }
        for(int i=0;i<procesamientos.getAborted().size();i++){
            res+=procesamientos.getAborted().get(i)+"\n";
        }
        System.out.println(res);
    }
    
    public void imprimirRespuestas(){
        System.out.println("Cadena: "+this.cadena+"\n");
        procesarCadena();
        procesarCadenaConDetalles();
        computarTodosLosProcesamientos();
    }
    
    
    
}
