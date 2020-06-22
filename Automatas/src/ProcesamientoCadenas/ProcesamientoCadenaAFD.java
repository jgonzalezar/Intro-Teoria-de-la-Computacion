package ProcesamientoCadenas;

import java.util.ArrayList;

/**
 * representa el procesamiento realiza a una cadena a travez de los estados de un AFD
 * definiendo los estados por los que pasa,junto con la cadena y su estado de aceptacion
 * @author equipo Los Javas
 * 
 */
public class ProcesamientoCadenaAFD  {
    private String cadena;
    private boolean esAceptada;
    private final ArrayList<String> listaEstadoSimboloDeProcesamiento;

    /**
     * Constructor del procesamiento donde guarda alguna cadena inicial eh crea un nuevo objeto para el resto de variables
     * @param cadena cadena a guardar en el procesamiento
     */
    
    
    public ProcesamientoCadenaAFD(String cadena) {
        esAceptada = false;
        this.cadena = cadena;
        listaEstadoSimboloDeProcesamiento = new ArrayList<>();
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
     */
    public void add(String paso){
        listaEstadoSimboloDeProcesamiento.add(paso);
        
    }
    
    /**
     * devuelve una cadena de todos los estados procesados de a pares con la parte de la cadena faltante por procesar
     * @return camino nuevo
     */
    
    public String pasos(){
        return pasos(cadena,listaEstadoSimboloDeProcesamiento,0);
    }
    
    public static String pasos(String Cadena, ArrayList<String> estados,int i){
        String s ="";
        try{
            s += "["+estados.get(i)+", "+Cadena.substring(i)+"]-> "+ pasos(Cadena, estados,i+1);
        }catch(NullPointerException | IndexOutOfBoundsException e){
            return s;
        }
        //IndexOutOfBoundsException
        return s;
    }

    public String getlastPaso() {
        return listaEstadoSimboloDeProcesamiento.get(listaEstadoSimboloDeProcesamiento.size()-1);
    }
    
}