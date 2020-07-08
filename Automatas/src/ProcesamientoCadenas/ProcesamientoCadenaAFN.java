package ProcesamientoCadenas;

import java.util.ArrayList;

/**
 * Esta clase se utiliza para la impresión de los diferentes procesamientos de una cadena en un automata
 * @author jdacostabe
 */
public class ProcesamientoCadenaAFN {
    /**
     * El atributo cadena se utiliza para guardar la cadena a la cual pertenecen los procesamientos que se imprimirán.
     */
    public String cadena;
    /**
     * El atributo q0 se utiliza para saber cual es el estado inicial del procesamiento.
     */
    public Integer q0;
    /**
     * El atributo aceptado es un tipo de dato que guarda todos los procesamientos aceptados de una cadena.
     */
    public ArrayList<Integer> aceptado;
    /**
     * El atributo rechazado es un tipo de dato que guarda todos los procesamientos rechazados de una cadena.
     */
    public ArrayList<Integer> rechazado;
    /**
     * El atributo abortado es un tipo de dato que guarda todos los procesamientos rechazados de una cadena.
     */    
    public ArrayList<Integer> abortado;
    
    private final ArrayList<String> Q;
    
    /**
     * Constructor, inicializa los atributos.
     * @param cadena Cadena a la cual se le realizará el procesamiento en el automata
     * @param q0 Estado desde el cual se incial el procesamiento de la cadena
     * @param Q nombres de los estados del AFN
     */
    public ProcesamientoCadenaAFN(String cadena, Integer q0, ArrayList<String> Q) {
        this.cadena = cadena;
        this.q0=q0;
        this.Q = Q;
        aceptado = new ArrayList<>();
        rechazado = new ArrayList<>();
        abortado = new ArrayList<>();
    }
    
    /**
     * Función que añade un paso de un procesamiento aceptado.
     * @param paso Estado en el cual se encuentra el paso del procesamiento
     */
    public void addAceptado(Integer paso){
        aceptado.add(paso);       
    }
    
    /**
     * Función que añade un paso de un procesamiento rechazado.
     * @param paso Estado en el cual se encuentra el paso del procesamiento
     */
    public void addRechazado(Integer paso){
        rechazado.add(paso);       
    }
    
    /**
     * Función que añade un paso de un procesamiento abortado.
     * @param paso Estado en el cual se encuentra el paso del procesamiento
     */
    public void addAbortado(Integer paso){
        abortado.add(paso);       
    }
    
    /**
     * Función que retorna un arreglo con todos los procesamientos aceptados.
     * @return ArrayList de procesamientos aceptados
     */
    public ArrayList<String> getAccepted(){
        ArrayList<String> accepted = new ArrayList<>();
        String s;
        if(cadena.length()==0){
            if(aceptado.isEmpty()){
                return accepted;
            }
            accepted.add("["+Q.get(q0)+", ]");
            return accepted;
        }
        for(int i=0;i<aceptado.size()/cadena.length();i++){
            s="["+Q.get(q0)+","+cadena+"]";
            for(int j=0;j<cadena.length()-1;j++){
                s += " -> ["+Q.get(aceptado.get(j+i*cadena.length()))+","+cadena.substring(j+1)+"]";
            }
            s += " -> ["+Q.get(aceptado.get(cadena.length()-1+i*cadena.length()))+", ]";
            accepted.add(s);
        }
        return accepted;
    }
    
    /**
     * Función que retorna un arreglo con todos los procesamientos rechazados.
     * @return ArrayList de procesamientos rechazados
     */
    public ArrayList<String> getRejected(){
        ArrayList<String> rejected = new ArrayList<>();
        String s;
        if(cadena.length()==0){
            if(rechazado.isEmpty()){
                return rejected;
            }
            rejected.add("["+Q.get(q0)+", ]");
            return rejected;
        }
        for(int i=0;i<rechazado.size()/cadena.length();i++){
            s="["+Q.get(q0)+","+cadena+"]";
            for(int j=0;j<cadena.length()-1;j++){
                s += " -> ["+Q.get(rechazado.get(j+i*cadena.length()))+","+cadena.substring(j+1)+"]";
            }
            s += " -> ["+Q.get(rechazado.get(cadena.length()-1+i*cadena.length()))+", ]";
            rejected.add(s);
        }
        return rejected;
    }
    
    /**
     * Función que retorna un arreglo con todos los procesamientos abortados.
     * @return ArrayList de procesamientos abortados
     */
    public ArrayList<String> getAborted(){
        ArrayList<String> aborted = new ArrayList<>();
        String s="["+Q.get(q0)+","+cadena+"]";
        int cont=0;
        for(int i=0;i<abortado.size();i++){
            if(abortado.get(i)==-1){
                aborted.add(s);
                s="["+Q.get(q0)+","+cadena+"]";
                cont=0;
            }else{
                s += " -> ["+Q.get(abortado.get(i))+","+cadena.substring(cont+1)+"]";
                cont++;
            }
        }
        return aborted;
    }
    
    /**
     * Función que retorna si la cadena es aceptada o no.
     * @return boolean que indica si la cadena es aceptada en el automata
     */
    public boolean isAccepted(){
        return aceptado.size()>0;      
    }
}
