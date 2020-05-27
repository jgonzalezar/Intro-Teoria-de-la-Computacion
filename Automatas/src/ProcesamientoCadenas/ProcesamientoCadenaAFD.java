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
    private ArrayList<String> listaEstadoSimboloDeProcesamiento;

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
     * @return 
     */

    public String getCadena(){
        return cadena;
    }
    
    

    public boolean EsAceptada() {
        return esAceptada;
    }

    public ArrayList<String> getListaEstadoSimboloDeProcesamiento() {
        return listaEstadoSimboloDeProcesamiento;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public void setEsAceptada(boolean esAceptada) {
        this.esAceptada = esAceptada;
    }
    public void add(String paso){
        listaEstadoSimboloDeProcesamiento.add(paso);
        
    }
    public String pasos(){
        String s ="";
        for (int i = 0; i < listaEstadoSimboloDeProcesamiento.size(); i++) {
            s += "["+listaEstadoSimboloDeProcesamiento.get(i)+", "+cadena.substring(i)+"]"+(char) 124+(char)61+(char)62;
        }
        return pasos(cadena,listaEstadoSimboloDeProcesamiento,0);
    }
    
    public static String pasos(String Cadena, ArrayList<String> estados,int i){
        String s ="";
        // += pasos(Cadena, estados,i+1);
        for (int i = 0; i < estados.size(); i++) {
            s += "["+estados.get(i)+", "+Cadena.substring(i)+"]-> ";
        }
        return s;
    }

    public String getlastPaso() {
        return listaEstadoSimboloDeProcesamiento.get(listaEstadoSimboloDeProcesamiento.size()-1);
    }
    
}