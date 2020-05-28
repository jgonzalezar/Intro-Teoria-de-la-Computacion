package ProcesamientoCadenas;

import Herramientas.RespuestaMult;
import java.util.ArrayList;

/**
 *
 * @author jdacostabe
 */
public class ProcesamientoCadenaAFNLambda {
    private String cadena;
    private boolean esAceptada;
    private ArrayList<String> listaProcesamientosAbortados;
    private ArrayList<String> listaProcesamientosAceptacion;
    private ArrayList<String> listaProcesamientosRechazados;

    public ProcesamientoCadenaAFNLambda(String cadena, ArrayList<String> estados, ArrayList<Integer> aceptados , RespuestaMult respuesta) {
        this.cadena = cadena;
        listaProcesamientosAbortados = new ArrayList<>();
        listaProcesamientosAceptacion = new ArrayList<>();
        listaProcesamientosRechazados = new ArrayList<>();
        
        ArrayList<Integer> resFinals = respuesta.getFinals();
        
        for (int i = 0; i < resFinals.size(); i++) {
            ArrayList<String> ruta = new ArrayList<>();
            for (int j = 0; j < respuesta.getCamino(i).size(); j++) {
                ruta.add(estados.get(respuesta.getCamino(i).get(j)));
            }
            String camino = ProcesamientoCadenaAFD.pasos(cadena, ruta, 0);
            try{
                if(resFinals.get(i) == null){
                    throw new NullPointerException();
                }
                if(aceptados.contains(resFinals.get(i))){
                    listaProcesamientosAceptacion.add(camino + " Aceptado");
                }else{
                    listaProcesamientosRechazados.add(camino + " Rechazado");
                }
            }catch(NullPointerException e){
                listaProcesamientosAbortados.add(camino + " Abortado");
            }
        }
        
        esAceptada = !listaProcesamientosAceptacion.isEmpty();
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
