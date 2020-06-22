package ProcesamientoCadenas;

import Herramientas.Tupla;
import java.util.ArrayList;

/**
 * Esta clase se encarga de guardar los totales de los procesamientos aceptados,
 * rechazados y abortados de una cadena dada junto con un atributo booleano que
 * nos dice si la cadena es o no aceptada por el automata.
 *
 * @author ivonn, fnat, jgonzalezar
 */
public class ProcesamientoCadenaAFNLambda {

    /**
     * El atributo cadena representa la cadena a evaluar.
     */
    private String cadena;
    /**
     * El atributo esAcptada retorna verdadero si la lista de procesamientos
     * aceptados no está vacía.
     */
    private final boolean esAceptada;
    /**
     * El atributo listaProcesamientosAbortados guarda los procesamientos
     * abortados.
     */
    private ArrayList<String> listaProcesamientosAbortados;
    /**
     * El atributo listaProcesamientosAceptacion guarda los procesamientos
     * aceptados.
     */
    private ArrayList<String> listaProcesamientosAceptacion;
    /**
     * El atributo listaProcesamientosRechazados guarda los procesamientos
     * rechazados.
     */
    private ArrayList<String> listaProcesamientosRechazados;

    /**
     * Constructor, inicializa los atributos de la clase.
     *
     * @param cadena Cadena a evaluar.
     * @param tupla estado y camino recorrido desde ese estado.
     */
    public ProcesamientoCadenaAFNLambda(String cadena, ArrayList<Tupla> tupla) {
        this.cadena = cadena;
        listaProcesamientosAbortados = new ArrayList<>();
        listaProcesamientosAceptacion = new ArrayList<>();
        listaProcesamientosRechazados = new ArrayList<>();

        for (Tupla tupla1 : tupla) {
            switch (tupla1.estado) {
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

    /**
     * Imprime el camino más corto de acuerdo a si es aceptado, rechazado o
     * abortado.
     *
     * @return Cadena con el camino.
     */
    public String imprimirCamino() {
        String camino;
        if (esAceptada) {
            int indice = 0;

            for (int i = 1; i < listaProcesamientosAceptacion.size(); i++) {
                if (listaProcesamientosAceptacion.get(indice).length() > listaProcesamientosAceptacion.get(i).length()) {
                    indice = i;
                }
            }
            camino = listaProcesamientosAceptacion.get(indice);
        } else if (!listaProcesamientosRechazados.isEmpty()) {
            int indice = 0;

            for (int i = 1; i < listaProcesamientosRechazados.size(); i++) {
                if (listaProcesamientosRechazados.get(indice).length() > listaProcesamientosRechazados.get(i).length()) {
                    indice = i;
                }
            }
            camino = listaProcesamientosRechazados.get(indice);
        } else {
            int indice = 0;

            for (int i = 1; i < listaProcesamientosAbortados.size(); i++) {
                if (listaProcesamientosAbortados.get(indice).length() > listaProcesamientosAbortados.get(i).length()) {
                    indice = i;
                }
            }
            camino = listaProcesamientosAbortados.get(indice);
        }
        return camino;
    }

    /**
     * Retorna la cadena ingresada.
     *
     * @return La cadena.
     */
    public String getCadena() {
        return cadena;
    }

    /**
     * Verifica si la cadena es aceptada.
     *
     * @return Verdadero si se acepta, falso, de lo contrario.
     */
    public boolean isEsAceptada() {
        return esAceptada;
    }

    /**
     * Obtiene la lista de procesamientos abortados.
     *
     * @return Lista de procesamientos.
     */
    public ArrayList<String> getListaProcesamientosAbortados() {
        return listaProcesamientosAbortados;
    }

    /**
     * Obtiene la lista de procesamientos aceptados.
     *
     * @return Lista de procesamientos.
     */
    public ArrayList<String> getListaProcesamientosAceptacion() {
        return listaProcesamientosAceptacion;
    }

    /**
     * Obtiene la lista de procesamientos rechazados.
     *
     * @return Lista de procesamientos.
     */
    public ArrayList<String> getListaProcesamientosRechazados() {
        return listaProcesamientosRechazados;
    }

}
