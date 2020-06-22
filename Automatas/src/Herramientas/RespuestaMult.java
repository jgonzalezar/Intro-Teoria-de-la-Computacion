package Herramientas;

import java.util.ArrayList;

/**
 * clase para almacenar una lista de caminos recorridos
 *
 * @author fanat
 */
public class RespuestaMult {

    public ArrayList<ArrayList<Integer>> pasos;

    public RespuestaMult() {
        pasos = new ArrayList<>();
    }

    /**
     * añade un nuevo comienzo de camino, es decir que es el inicio
     *
     * @param paso inicio de camino
     */
    public void add(int paso) {
        pasos.add(new ArrayList<>());
        pasos.get(pasos.size() - 1).add(paso);
    }

    /**
     * añade un paso a una ruta, ya sea sobre la misma o creando una ruta nueva
     * con los pasos de la ruta dada
     *
     * @param paso paso a avanzar en la ruta
     * @param ruta ruta a tomar para seguir (lista de estados por los que paso
     * previamente, dados en un indice del array de estados)
     * @param add crear una copia de ruta o modificar la misma ruta dada
     */
    public void addRuta(Integer paso, int ruta, boolean add) {
        if (add) {
            pasos.add(new ArrayList<>());
            pasos.get(ruta).forEach((get) -> {
                pasos.get(pasos.size() - 1).add(get);
            });
            pasos.get(pasos.size() - 1).add(paso);
        } else {
            pasos.get(ruta).add(paso);
        }
    }

    public void addRutas(int ruta, Integer... paso) {
        ArrayList<Integer> rutas = getCamino(ruta);
        for (int i = 0; i < paso.length - 1; i++) {
            ArrayList<Integer> rutaNueva = new ArrayList<>();
            rutaNueva.addAll(rutas);
            rutaNueva.add(paso[i]);
            pasos.add(rutaNueva);
        }
        pasos.get(ruta).add(paso[paso.length - 1]);
    }

    public void addRutas(int ruta, ArrayList<Integer> paso) {
        ArrayList<Integer> rutas = getCamino(ruta);
        for (int i = 0; i < paso.size() - 1; i++) {
            ArrayList<Integer> rutaNueva = new ArrayList<>();
            rutaNueva.addAll(rutas);
            rutaNueva.add(paso.get(i));
            pasos.add(rutaNueva);
        }
        pasos.get(ruta).add(paso.get(paso.size() - 1));
    }

    /**
     * añade una ruta nueva totalmente
     *
     * @param ruta ruta nueva a agregar
     */

    public void addRuta(ArrayList<Integer> ruta) {
        pasos.add(ruta);
    }

    /**
     * retorna los estados finales de todos los caminos
     *
     * @return lista de estados finales en todos los caminos, los indices de la
     * lista son los mismo que los caminos guardados
     */
    public ArrayList<Integer> getFinals() {
        ArrayList<Integer> set = new ArrayList<>();
        pasos.forEach((paso) -> {
            set.add(paso.get(paso.size() - 1));
        });

        return set;
    }

    /**
     * retorna los pasos de un camino pedido por indice
     *
     * @param ruta indice del camino requerido
     * @return lista de pasos
     */

    public ArrayList<Integer> getCamino(int ruta) {
        return pasos.get(ruta);
    }

    /**
     * remueve una ruta dada por indice
     *
     * @param ruta indice de ruta pedida
     * @return camino eliminado
     */
    public ArrayList<Integer> removeCamino(int ruta) {
        ArrayList<Integer> a = getCamino(ruta);
        pasos.remove(ruta);
        return a;
    }

}
