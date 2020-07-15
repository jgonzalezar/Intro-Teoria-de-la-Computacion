package ProcesamientoCadenas;

import Herramientas.ParPila;
import java.util.ArrayList;
import java.util.Stack;

/**
 * representa el procesamiento realiza a una cadena a travez de los estados de un AFD
 * definiendo los estados por los que pasa,junto con la cadena y su estado de aceptacion
 * @author equipo Los Javas
 * 
 */
public class ProcesamientoCadenaTM  {
    private String cadena;
    private boolean esAceptada;
    private final ArrayList<String> listaEstadoSimboloDeProcesamiento;
    private ArrayList<String> lista;
    private ArrayList<Integer> listas;
    private int ind;

    /**
     * Constructor del procesamiento donde guarda alguna cadena inicial eh crea un nuevo objeto para el resto de variables
     * @param cadena cadena a guardar en el procesamiento
     * @param paso1
     * @param blnc
     */
    
    
    public ProcesamientoCadenaTM(String cadena,String paso1,Character blnc) {
        esAceptada = false;
        int d = 0;
        if(cadena.charAt(0)!= blnc){
            d++;
            cadena = blnc+cadena;
        }
        if(cadena.charAt(cadena.length()-1)!=blnc){
            cadena = cadena+blnc;
        }
        this.cadena = cadena;
        listaEstadoSimboloDeProcesamiento = new ArrayList<>();
        listaEstadoSimboloDeProcesamiento.add(paso1);
        lista= new ArrayList<>();
        lista.add(cadena);
        listas= new ArrayList<>();
        listas.add(d);
        ind=d;
    }
    
    /**
     * get de la cadena que es procesada
     *  @return cadena 
     */

    public String getCadena(){
        return cadena;
    }
    
    /**
     * si la cadena es aceptada o no
     * @return esAceptada
     */

    public boolean EsAceptada() {
        return esAceptada;
    }
    
    /**
     * Lista de simbolos estados por los que paso el procesamiento
     * @return lista de estados de procesamiento
     */

    public ArrayList<String> getListaEstadoSimboloDeProcesamiento() {
        return listaEstadoSimboloDeProcesamiento;
    }

    /**
     * cambio de cadena procesada
     * @param cadena nueva cadena
     */
    
    public void setCadena(String cadena) {
        this.cadena = cadena;
    }
    
    /**
     * cambio del booleano de acpetacion
     * @param esAceptada nuevo esAceptada
     */

    public void setEsAceptada(boolean esAceptada) {
        this.esAceptada = esAceptada;
    }
    
    /**
     * añade un estado a la lista de estados procesados
     * @param paso nuevo estado agregado
     * @param dir
     * @param in
     */
    public void add(String paso,Character in,String dir){
        listaEstadoSimboloDeProcesamiento.add(paso);
        setCad(in,dir);
        lista.add(cadena);
    }
    
    private void setCad(Character in,String dir){
        if(cadena.charAt(ind)!=in){
            if(ind==cadena.length()-1){
                cadena=cadena.substring(0, ind)+in;
            }else{
                cadena=cadena.substring(0, ind)+in+cadena.substring(ind+1);
            }
        }
        switch(dir){
            case "+":
                ind++;
                break;
            case "-":
                ind--;
                break;                
        }
        listas.add(ind);
    }
    
    /**
     * devuelve una cadena de todos los estados procesados de a pares con la parte de la cadena faltante por procesar
     * @return camino nuevo
     */
    
    public String pasos(){
        return pasos(cadena,listaEstadoSimboloDeProcesamiento,lista,listas,0);
    }
    
    public static String pasos(String Cadena, ArrayList<String> estados,ArrayList<String> pila,ArrayList<Integer> pilas,int i){
        String s ="";
        try{
            s += "["+estados.get(i)+", "+pila.get(i)+"]-> "+ pilas.get(i)+"]-> "+pasos(Cadena, estados,pila,pilas,i+1);
        }catch(NullPointerException | IndexOutOfBoundsException e){
            return s;
        }
        //IndexOutOfBoundsException
        return s;
    }

    public ParPila getlastPaso() {
        return new ParPila(cadena.charAt(ind),listaEstadoSimboloDeProcesamiento.get(listaEstadoSimboloDeProcesamiento.size()-1));
    }
    public String gatLastSta(){
        return listaEstadoSimboloDeProcesamiento.get(listaEstadoSimboloDeProcesamiento.size()-1);
    }
    public String getLastCadena(){
        return cadena.substring(ind);
    }
}