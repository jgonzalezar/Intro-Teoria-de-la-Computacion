package ProcesamientoCadenas;

import Herramientas.NDRespuesta;
import java.util.ArrayList;

/**
 * Esta clase se utiliza para la impresión de los diferentes procesamientos de una cadena en un automata
 * @author jdacostabe
 */
public class ProcesamientoCadenaAFN {
    /**
     * El atributo cadena se utiliza para guardar la cadena a la cual pertenecen los procesamientos que se imprimirán.
     */
    String cadena;
    /**
     * El atributo isAccepted se utiliza para indicar si la cadena es aceptada o no.
     */
    boolean isAccepted;
    /**
     * El atributo procesamientos es un tipo de dato que guarda todos los procesamientos de una cadena (Aceptados, rechazados o abortados).
     */
    NDRespuesta procesamientos;
    
    /**
     * Constructor, inicializa los atributos.
     * @param cadena Cadena a la cual se le realizó el procesamiento en el automata
     * @param procesamientos Lista de procesamientos aceptados, rechazados y abortados
     */
    public ProcesamientoCadenaAFN(String cadena, NDRespuesta procesamientos){
        this.cadena = cadena;
        this.isAccepted = procesamientos.isAccepted();
        this.procesamientos = procesamientos;
    }
    
    /**
     * Imprime si la cadena es aceptada o no.
     */
    public void procesarCadena(){
        System.out.println("Procesar cadena: "+ isAccepted+"\n");
    }
    
    /**
     * Imprime un solo procesamiento de la cadena dependiendo de si es aceptado, rechazado o no tiene procesamientos terminados
     */
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
    
    /**
     * Imprime todos los procesamiento de la cadena (Aceptados, rechazados y abortados)
     */
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
    
    /**
     * Realiza todos los tipos de impresiones de los procesamientos de la cadena.
     */
    public void imprimirRespuestas(){
        System.out.println("Cadena: "+this.cadena+"\n");
        procesarCadena();
        procesarCadenaConDetalles();
        computarTodosLosProcesamientos();
    }
    
    
    
}
