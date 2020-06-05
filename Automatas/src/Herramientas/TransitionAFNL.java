package Herramientas;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Esta clase representa la estructura de la transicion de los automatas finitos
 * no deterministas con transiciones lambda.
 *
 * @author ivonn
 */
public class TransitionAFNL implements Transitions{

    /**
     * El atributo transicionAFNL representa la transición.
     */
     ArrayList<HashMap<Character, ArrayList<Integer>>> transicionAFNL;

    /**
     * constructor de un arraylist de hashmaps de tamaño indice
     *
     * @param indice numero de estados del automata;
     */
    public TransitionAFNL(int indice) {
        transicionAFNL = new ArrayList<>();
        for (int i = 0; i < indice; i++) {
            transicionAFNL.add(new HashMap<>());
        }
    }

    /**
     * Agrega a cada uno de los estados una lista de parejas de un caracter del
     * alfabeto del utomata y una lista de estados a los ue transiciona con este
     * caracter.
     *
     * @param Alphabeto caracter del alfabeto del automata;
     * @param Estado estado al cual se le estan agregndo valores;
     * @param estado lista de los estados a los que estado transiciona con el
     * caracter dado;
     */
    
     @Override
    public void add(Character Alphabeto, int Estado, int... estado) {
        ArrayList<Integer> state = new ArrayList<>();
        for (int i = 0; i < estado.length; i++) {
            state.add(estado[i]);
        }
        transicionAFNL.get(Estado).put(Alphabeto, state);
    }

    /**
     * Agrega hashmaps de transicion a cada estado que tenemos.
     *
     * @param letter_states estados.
     */
    public void Add(HashMap letter_states) {
        transicionAFNL.add(letter_states);
    }

    /**
     * retorna el tamaño del array de hashmaps
     *
     * @return Tamaño.
     */
     @Override
    public int size() {
        return transicionAFNL.size();
    }

    /**
     * retorna la lista de los estados a los que un estado va con un caracter
     * dado
     *
     * @param c caracter del alfabeto del automata que se evalua desde el estado
     * i;
     * @param i estado en el cual estamos;
     * @return lista de estados a los que pasa eveluando el caracter dado.
     */
     @Override
    public ArrayList<Integer> getMove(int i, Character c) {
        return transicionAFNL.get(i).get(c);
    }

    @Override
    public void add(Character Alphabeto, String Estado, String estado) {
    }

    @Override
    public String cambio(Character Alphabeto, String Estado) {
        return null;
    }

    @Override
    public void add(Integer initialState, String symbol, Integer finalState) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Tuple> get(int state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void add(Character Alphabeto, String Inest, Character InPila, String estadoSig, Character ToPila) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ParPila cambio(Character Alphabeto, String Inest, Character InPila) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
