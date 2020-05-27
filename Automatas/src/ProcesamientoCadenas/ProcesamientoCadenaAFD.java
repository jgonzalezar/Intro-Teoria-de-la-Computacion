package ProcesamientoCadenas;

import java.util.ArrayList;

/**
 *
 *
 */
public class ProcesamientoCadenaAFD  {
    private String cadena;
    private boolean esAceptada;
    private ArrayList<String> listaEstadoSimboloDeProcesamiento;

    public ProcesamientoCadenaAFD(String cadena) {
        esAceptada = false;
        this.cadena = cadena;
        listaEstadoSimboloDeProcesamiento = new ArrayList<>();
    }
    

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
        return s;
    }
    
    public static String pasos(String Cadena, ArrayList<String> estados){
        String s ="";
        for (int i = 0; i < estados.size(); i++) {
            s += "["+estados.get(i)+", "+Cadena.substring(i)+"]-> ";
        }
        return s;
    }

    public String getlastPaso() {
        return listaEstadoSimboloDeProcesamiento.get(listaEstadoSimboloDeProcesamiento.size()-1);
    }
    
}