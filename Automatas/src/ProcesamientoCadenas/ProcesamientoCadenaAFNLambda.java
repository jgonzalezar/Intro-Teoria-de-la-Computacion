package ProcesamientoCadenas;

import Herramientas.RespuestaMult;
import Herramientas.Tupla;
import java.util.ArrayList;

/**
 *
 * @author jgonzalezar, ivonn, fnat
 */
public class ProcesamientoCadenaAFNLambda {
    private String cadena;
    private boolean esAceptada;
    private ArrayList<String> listaProcesamientosAbortados;
    private ArrayList<String> listaProcesamientosAceptacion;
    private ArrayList<String> listaProcesamientosRechazados;

    public ProcesamientoCadenaAFNLambda(String cadena, ArrayList<Tupla> tupla) {
        this.cadena = cadena;
        listaProcesamientosAbortados = new ArrayList<>();
        listaProcesamientosAceptacion = new ArrayList<>();
        listaProcesamientosRechazados = new ArrayList<>();
        
        for (Tupla tupla1 : tupla) {
            switch(tupla1.estado){
                case Tupla.Case_Aceptado:
                    listaProcesamientosAceptacion.add(tupla1.caracter);
                    break;
                case Tupla.Case_Rechazado:
                    listaProcesamientosRechazados.add(tupla1.caracter);
                    break;
                case Tupla.Case_Abortado:
                    listaProcesamientosAbortados.add(tupla1.caracter);
                    break;
                    
            }
        }
        
        esAceptada = !listaProcesamientosAceptacion.isEmpty();
    }
    
    public String imprimirCamino (){
        String camino;
        if(esAceptada){
            camino = listaProcesamientosAceptacion.get(0);  
        }else if (!listaProcesamientosRechazados.isEmpty()){
            camino = listaProcesamientosRechazados.get(0);
        }else{
            camino = listaProcesamientosAbortados.get(0);
        }
        return camino;
    }

    public String getCadena() {
        return cadena;
    }

    public boolean isEsAceptada() {
        return esAceptada;
    }

    public ArrayList<String> getListaProcesamientosAbortados() {
        return listaProcesamientosAbortados;
    }

    public ArrayList<String> getListaProcesamientosAceptacion() {
        return listaProcesamientosAceptacion;
    }

    public ArrayList<String> getListaProcesamientosRechazados() {
        return listaProcesamientosRechazados;
    }
    
    
    
}
